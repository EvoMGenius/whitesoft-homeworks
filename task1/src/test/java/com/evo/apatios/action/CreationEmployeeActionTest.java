package com.evo.apatios.action;

import com.evo.apatios.action.argument.CreationEmployeeActionArgument;
import com.evo.apatios.exception.NotFoundPostException;
import com.evo.apatios.model.Employee;
import com.evo.apatios.model.Post;
import com.evo.apatios.service.employee.EmployeeService;
import com.evo.apatios.service.post.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreationEmployeeActionTest {

    private final EmployeeService employeeService = Mockito.mock(EmployeeService.class);

    private final PostService postService = Mockito.mock(PostService.class);

    private final CreationEmployeeAction action = new CreationEmployeeAction(employeeService, postService);

    private final UUID postId = UUID.randomUUID();

    @Test
    void executeCreationWithExistingPost() {
        //arrange
        CreationEmployeeActionArgument argument = CreationEmployeeActionArgument.builder()
                .id(UUID.randomUUID())
                .firstName("Petr")
                .postId(postId)
                .build();
        Post post = new Post(postId, "post name");

        when(postService.getExistingById(postId)).thenReturn(post);

        when(employeeService.create(any())).thenReturn(Employee.builder()
                        .id(argument.getId())
                        .firstName(argument.getFirstName())
                        .post(post)
                        .build());


        //act

        Employee createdEmployee = action.execute(argument);

        //assert

        Assertions.assertEquals(createdEmployee, Employee.builder()
                        .id(argument.getId())
                        .firstName(argument.getFirstName())
                        .post(post)
                .build());

        verify(employeeService).create(any());
        verify(postService).getExistingById(postId);
    }


    @Test
    void executeCreationWithNotExistingPost() {
        //arrange
        CreationEmployeeActionArgument argument = CreationEmployeeActionArgument.builder()
                .id(UUID.randomUUID())
                .firstName("Petr")
                .postId(postId)
                .build();
        Post post = new Post(postId, "post name");

        when(postService.getExistingById(postId)).thenThrow(NotFoundPostException.class);

        when(employeeService.create(any())).thenReturn(Employee.builder()
                .id(argument.getId())
                .firstName(argument.getFirstName())
                .post(post)
                .build());


        //act + assert

        Assertions.assertThrows(NotFoundPostException.class, ()->{
            action.execute(argument);
        });

        verify(employeeService, never()).create(any());
        verify(postService).getExistingById(postId);
    }
}