package com.evo.apatios.action;

import com.evo.apatios.action.argument.UpdateEmployeeActionArgument;
import com.evo.apatios.model.Employee;
import com.evo.apatios.service.argument.employee.UpdateEmployeeArgument;
import com.evo.apatios.service.employee.EmployeeService;
import com.evo.apatios.service.post.PostService;
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
