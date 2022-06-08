package com.evo.apatios.action;

import com.evo.apatios.service.CreationEmployeeArgument;
import com.evo.apatios.argument.CreationEmployeeActionArgument;
import com.evo.apatios.exception.IllegalPostNameException;
import com.evo.apatios.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.evo.apatios.service.EmployeeService;
import com.evo.apatios.service.PostService;

@Component
public class AddEmployeeAction {

    public final EmployeeService employeeService;

    public final PostService postService;

    @Autowired
    public AddEmployeeAction(EmployeeService employeeService, PostService postService) {
        this.employeeService = employeeService;
        this.postService = postService;
    }

    public Employee execute(CreationEmployeeActionArgument employeeArgument){
        CreationEmployeeArgument argumentForService = CreationEmployeeArgument.builder()
                .firstName(employeeArgument.getFirstName())
                .lastName(employeeArgument.getLastName())
                .characteristics(employeeArgument.getCharacteristics())
                .description(employeeArgument.getDescription())
                .post(postService.findById(employeeArgument.getPostId()).orElseThrow(()-> {
                    throw new IllegalPostNameException(String.format("Invalid post UUID %s", employeeArgument.getPostId().toString()));
                }))
                .build();

        return employeeService.create(argumentForService);
    }
}
