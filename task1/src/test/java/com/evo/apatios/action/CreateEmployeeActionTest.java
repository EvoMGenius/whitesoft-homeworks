package com.evo.apatios.action;

import com.evo.apatios.action.argument.CreateEmployeeActionArgument;
import com.evo.apatios.exception.NotFoundException;
import com.evo.apatios.model.Contacts;
import com.evo.apatios.model.Employee;
import com.evo.apatios.model.JobType;
import com.evo.apatios.model.Post;
import com.evo.apatios.service.argument.employee.CreateEmployeeArgument;
import com.evo.apatios.service.employee.EmployeeService;
import com.evo.apatios.service.post.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Comparator;
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

        Post post = mock(Post.class);

        Employee expectedEmployee = mock(Employee.class);

        ArgumentCaptor<CreateEmployeeArgument> employeeArgumentCaptor = ArgumentCaptor.forClass(CreateEmployeeArgument.class);

        when(postService.getExistingById(postId)).thenReturn(post);

        when(employeeService.create(employeeArgumentCaptor.capture())).thenReturn(expectedEmployee);
        //act
        Employee createdEmployee = action.execute(argument);
        CreateEmployeeArgument capturedEmployeeArgument = employeeArgumentCaptor.getValue();
        //assert
        Assertions.assertEquals(createdEmployee, expectedEmployee);
        assertThat(capturedEmployeeArgument).usingRecursiveComparison()
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

        Post post = mock(Post.class);
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