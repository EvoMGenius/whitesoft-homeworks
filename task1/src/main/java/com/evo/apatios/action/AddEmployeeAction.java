package com.evo.apatios.action;

import com.evo.apatios.argument.CreationEmployeeArgumentForService;
import com.evo.apatios.argument.CreationEmployeeArgument;
import com.evo.apatios.exception.IllegalPostNameException;
import com.evo.apatios.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.evo.apatios.service.EmployeeService;
import com.evo.apatios.service.PostService;

@Component
@Slf4j
public class AddEmployeeAction {

    public final EmployeeService employeeService;

    public final PostService postService;

    @Autowired
    public AddEmployeeAction(EmployeeService employeeService, PostService postService) {
        this.employeeService = employeeService;
        this.postService = postService;
    }

    public Employee execute(CreationEmployeeArgument employeeArgument){
        CreationEmployeeArgumentForService argumentForService = CreationEmployeeArgumentForService.builder()
                .firstName(employeeArgument.getFirstName())
                .lastName(employeeArgument.getLastName())
                .characteristics(employeeArgument.getCharacteristics())
                .description(employeeArgument.getDescription())
                .post(postService.findById(employeeArgument.getPostId()).orElseThrow(()-> {
                    throw new IllegalPostNameException(String.format("Invalid post UUID %s", employeeArgument.getPostId().toString()));
                }))
                .build();

        log.info(argumentForService.toString());
        return employeeService.create(argumentForService);
    }
}
