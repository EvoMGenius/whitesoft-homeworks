package com.evo.bunkov.action;

import com.evo.bunkov.action.argument.CreateEmployeeActionArgument;
import com.evo.bunkov.model.Employee;
import com.evo.bunkov.service.argument.employee.CreateEmployeeArgument;
import com.evo.bunkov.service.employee.EmployeeService;
import com.evo.bunkov.service.post.PostService;
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
