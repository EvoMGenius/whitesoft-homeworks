package com.evo.apatios.action;

import com.evo.apatios.argument.UpdateEmployeeActionArgument;
import com.evo.apatios.exception.IllegalPostIdException;
import com.evo.apatios.model.Employee;
import com.evo.apatios.service.CreationEmployeeArgument;
import com.evo.apatios.service.EmployeeService;
import com.evo.apatios.service.PostService;
import com.evo.apatios.service.UpdateEmployeeArgument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateEmployeeAction {

    public final EmployeeService employeeService;

    public final PostService postService;

    @Autowired
    public UpdateEmployeeAction(EmployeeService employeeService, PostService postService) {
        this.employeeService = employeeService;
        this.postService = postService;
    }

    public Employee execute(UpdateEmployeeActionArgument argument) {
        UpdateEmployeeArgument argumentForService = UpdateEmployeeArgument.builder()
                .id(argument.getId())
                .firstName(argument.getFirstName())
                .lastName(argument.getLastName())
                .description(argument.getDescription())
                .post(postService.findById(argument.getPostId()).orElseThrow(()->
                        new IllegalPostIdException(String.format("Incorrect postID - %s", argument.getPostId().toString()))
                ))
                .contacts(argument.getContacts())
                .characteristics(argument.getCharacteristics())
                .jobType(argument.getJobType())
                .build();

        return employeeService.update(argumentForService);

    }
}
