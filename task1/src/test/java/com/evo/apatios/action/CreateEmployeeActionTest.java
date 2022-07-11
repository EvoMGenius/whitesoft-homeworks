package com.evo.apatios.action;

import com.evo.apatios.action.argument.CreateEmployeeActionArgument;
import com.evo.apatios.exception.NotFoundException;
import com.evo.apatios.model.Employee;
import com.evo.apatios.model.Post;
import com.evo.apatios.service.employee.EmployeeService;
import com.evo.apatios.service.post.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

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
                                                                            .postId(postId)
                                                                            .build();

        Post post = mock(Post.class);

        Employee expectedEmployee = mock(Employee.class);

        when(postService.getExistingById(postId)).thenReturn(post);

        when(employeeService.create(any())).thenReturn(expectedEmployee);
        //act
        Employee createdEmployee = action.execute(argument);
        //assert
        Assertions.assertEquals(createdEmployee, expectedEmployee);
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