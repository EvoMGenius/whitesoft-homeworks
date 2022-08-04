package com.evo.bunkov.action;

import com.evo.bunkov.action.argument.CreateEmployeeActionArgument;
import com.evo.bunkov.exception.NotFoundException;
import com.evo.bunkov.model.Contacts;
import com.evo.bunkov.model.Employee;
import com.evo.bunkov.model.JobType;
import com.evo.bunkov.model.Post;
import com.evo.bunkov.service.argument.employee.CreateEmployeeArgument;
import com.evo.bunkov.service.employee.EmployeeService;
import com.evo.bunkov.service.post.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateEmployeeActionTest {

    private final EmployeeService employeeService = Mockito.mock(EmployeeService.class);

    private final PostService postService = Mockito.mock(PostService.class);

    private final CreateEmployeeAction action = new CreateEmployeeAction(employeeService, postService);

    private final UUID postId = UUID.randomUUID();

    @Test
    void executeCreationWithExistingPost() {
        //arrange
        CreateEmployeeActionArgument argument = CreateEmployeeActionArgument.builder()
                                                                            .firstName("Petr")
                                                                            .lastName("Petrov")
                                                                            .characteristics(List.of("leader", "smart"))
                                                                            .description("some description")
                                                                            .contacts(mock(Contacts.class))
                                                                            .jobType(JobType.CONTRACT)
                                                                            .postId(postId)
                                                                            .build();

        Post post = Post.builder()
                        .id(postId)
                        .name("post")
                        .build();

        Employee expectedEmployee = mock(Employee.class);

        when(postService.getExistingById(postId)).thenReturn(post);

        when(employeeService.create(any())).thenReturn(expectedEmployee);
        //act
        Employee createdEmployee = action.execute(argument);
        //assert
        ArgumentCaptor<CreateEmployeeArgument> employeeArgumentCaptor = ArgumentCaptor.forClass(CreateEmployeeArgument.class);

        verify(employeeService).create(employeeArgumentCaptor.capture());
        CreateEmployeeArgument capturedArgument = employeeArgumentCaptor.getValue();

        Assertions.assertEquals(createdEmployee, expectedEmployee);
        assertThat(capturedArgument.getPost().getId()).isEqualTo(argument.getPostId());
        assertThat(capturedArgument).usingRecursiveComparison()
                                    .ignoringFields("postId", "post")
                                    .isEqualTo(argument);
    }


    @Test
    void executeCreationWithNotExistingPost() {
        //arrange
        CreateEmployeeActionArgument argument = CreateEmployeeActionArgument.builder()
                                                                            .firstName("Petr")
                                                                            .postId(postId)
                                                                            .build();

        Employee expectedEmployee = mock(Employee.class);

        when(postService.getExistingById(postId)).thenThrow(NotFoundException.class);
        when(employeeService.create(any())).thenReturn(expectedEmployee);

        //act+assert
        Assertions.assertThrows(NotFoundException.class, () ->
                                        action.execute(argument)
                               );

        verify(employeeService, never()).create(any());
    }
}