package com.evo.bunkov.action;

import com.evo.bunkov.action.argument.UpdateEmployeeActionArgument;
import com.evo.bunkov.model.Employee;
import com.evo.bunkov.service.argument.employee.UpdateEmployeeArgument;
import com.evo.bunkov.service.employee.EmployeeService;
import com.evo.bunkov.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateEmployeeAction {

    public final EmployeeService employeeService;

    public final PostService postService;

    public Employee execute(UUID id, UpdateEmployeeActionArgument employeeArgument) {
        UpdateEmployeeArgument argumentForService = UpdateEmployeeArgument.builder()
                                                                          .id(id)
                                                                          .firstName(employeeArgument.getFirstName())
                                                                          .lastName(employeeArgument.getLastName())
                                                                          .description(employeeArgument.getDescription())
                                                                          .post(postService.getExistingById(employeeArgument.getPostId()))
                                                                          .contacts(employeeArgument.getContacts())
                                                                          .characteristics(employeeArgument.getCharacteristics())
                                                                          .jobType(employeeArgument.getJobType())
                                                                          .build();

        return employeeService.update(argumentForService);
    }
}
