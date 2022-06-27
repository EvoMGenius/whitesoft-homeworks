package com.evo.apatios.action;

import com.evo.apatios.action.argument.CreationEmployeeActionArgument;
import com.evo.apatios.service.argument.CreationEmployeeAgrument;
import com.evo.apatios.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.evo.apatios.service.employee.EmployeeService;
import com.evo.apatios.service.post.PostService;

@Component
@RequiredArgsConstructor
public class CreationEmployeeAction {

    public final EmployeeService employeeService;

    public final PostService postService;

    public Employee execute(CreationEmployeeActionArgument employeeArgument){
        CreationEmployeeAgrument argumentForService = CreationEmployeeAgrument.builder()
                .id(employeeArgument.getId())
                .firstName(employeeArgument.getFirstName())
                .lastName(employeeArgument.getLastName())
                .description(employeeArgument.getDescription())
                .post(postService.getExistingById(employeeArgument.getPostId()))
                .contacts(employeeArgument.getContacts())
                .characteristics(employeeArgument.getCharacteristics())
                .jobType(employeeArgument.getJobType())
                .build();

        return employeeService.create(argumentForService);
    }
}
