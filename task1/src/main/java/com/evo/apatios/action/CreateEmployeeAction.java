package com.evo.apatios.action;

import com.evo.apatios.action.argument.CreateEmployeeActionArgument;
import com.evo.apatios.model.Employee;
import com.evo.apatios.service.argument.employee.CreateEmployeeArgument;
import com.evo.apatios.service.employee.EmployeeService;
import com.evo.apatios.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateEmployeeAction {

    public final EmployeeService employeeService;

    public final PostService postService;

    public Employee execute(CreateEmployeeActionArgument employeeArgument) {
        CreateEmployeeArgument argumentForService = CreateEmployeeArgument.builder()
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
