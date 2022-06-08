package com.evo.apatios.action;

import com.evo.apatios.service.CreationEmployeeArgument;
import com.evo.apatios.argument.CreationEmployeeActionArgument;
import com.evo.apatios.exception.IllegalPostIdException;
import com.evo.apatios.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.evo.apatios.service.EmployeeService;
import com.evo.apatios.service.PostService;

@Component
public class CreationEmployeeAction {

    public final EmployeeService employeeService;

    public final PostService postService;

    @Autowired
    public CreationEmployeeAction(EmployeeService employeeService, PostService postService) {
        this.employeeService = employeeService;
        this.postService = postService;
    }

    public Employee execute(CreationEmployeeActionArgument employeeArgument){
        CreationEmployeeArgument argumentForService = CreationEmployeeArgument.builder()
                .id(employeeArgument.getId())
                .firstName(employeeArgument.getFirstName())
                .lastName(employeeArgument.getLastName())
                .description(employeeArgument.getDescription())
                .post(postService.findById(employeeArgument.getPostId()).orElseThrow(()->
                        new IllegalPostIdException(String.format("Incorrect postID - %s", employeeArgument.getPostId().toString()))
                ))
                .contacts(employeeArgument.getContacts())
                .characteristics(employeeArgument.getCharacteristics())
                .jobType(employeeArgument.getJobType())
                .build();

        return employeeService.create(argumentForService);
    }
}
