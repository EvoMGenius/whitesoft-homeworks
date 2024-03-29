package com.evo.bunkov.util.aspect;

import com.evo.bunkov.aspect.logging.UpdateEmployeeMethodLoggingAspect;
import com.evo.bunkov.model.Contacts;
import com.evo.bunkov.model.Employee;
import com.evo.bunkov.model.JobType;
import com.evo.bunkov.model.Post;
import com.evo.bunkov.service.argument.employee.UpdateEmployeeArgument;
import com.evo.bunkov.service.employee.EmployeeService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class UpdateEmployeeMethodLoggingAspectTest {
//
//    private final UUID id = UUID.randomUUID();
//
//    private final UUID postId = UUID.randomUUID();
//
//    private final Employee existedEmployee = Employee.builder()
//                                                     .id(id)
//                                                     .firstName("Ivan")
//                                                     .lastName("Ivanov")
//                                                     .post(Post.builder()
//                                                               .id(postId)
//                                                               .name("post")
//                                                               .build())
//                                                     .jobType(JobType.CONTRACT)
//                                                     .description("ds")
//                                                     .characteristics(List.of("Brave", "Smart"))
//                                                     .contacts(new Contacts("909", "email", "wemail"))
//                                                     .build();
//
//    private final EmployeeService employeeService = mock(EmployeeService.class);
//
//    private final UpdateEmployeeArgument employeeAfterUpdate = UpdateEmployeeArgument.builder()
//                                                                                     .id(id)
//                                                                                     .firstName("Ivan")
//                                                                                     .lastName("Ivanov")
//                                                                                     .post(Post.builder()
//                                                                                               .id(postId)
//                                                                                               .name("post")
//                                                                                               .build())
//                                                                                     .jobType(JobType.CONTRACT)
//                                                                                     .description("ds")
//                                                                                     .characteristics(List.of("Brave", "Smart"))
//                                                                                     .contacts(new Contacts("909", "email", "wemail"))
//                                                                                     .build();
//
//    private final UpdateEmployeeMethodLoggingAspect updateEmployeeMethodLoggingAspect = new UpdateEmployeeMethodLoggingAspect(employeeService);
//
//    @Test
//    void loggingUpdatedEmployeeFields() {
//        // arrange
//        when(employeeService.getExisting(id)).thenReturn(existedEmployee);
//        // act
//        updateEmployeeMethodLoggingAspect.saveLog(employeeAfterUpdate);
//        // assert
//        verify(employeeService).getExisting(id);
//    }
}