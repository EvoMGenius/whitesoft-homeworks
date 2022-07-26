package com.evo.apatios.util.aspect;

import com.evo.apatios.model.Employee;
import com.evo.apatios.service.argument.employee.UpdateEmployeeArgument;
import com.evo.apatios.service.employee.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Aspect
@Slf4j
@AllArgsConstructor
@ConditionalOnProperty(prefix = "logger", name = "update")
public class UpdateEmployeeMethodLoggingAspect {

    private final EmployeeService employeeService;

    @Pointcut("@annotation(com.evo.apatios.util.aspect.annotation.Log)")
    public void annotationPointcut() {}

    @Before(value = "annotationPointcut() && args(employee)",
            argNames = "employee")
    public void saveLog(UpdateEmployeeArgument employee) {
        Employee existedEmployee = employeeService.getExisting(employee.getId());
        log.info("Updating employee with id : {}, fields update : {}",
                 employee.getId(), getUpdatingFields(existedEmployee, employee));
    }

    private String getUpdatingFields(Employee employeeBeforeUpdate, UpdateEmployeeArgument employeeAfterUpdate) {
        StringBuilder sb = new StringBuilder();
        if (!Objects.equals(employeeBeforeUpdate.getFirstName(), employeeAfterUpdate.getFirstName())) {
            sb.append("firstName: before [").append(employeeBeforeUpdate.getFirstName())
              .append("] after [").append(employeeAfterUpdate.getFirstName()).append("]. ");
        }

        if (!Objects.equals(employeeBeforeUpdate.getLastName(), employeeAfterUpdate.getLastName())) {
            sb.append("lastName: before [").append(employeeBeforeUpdate.getLastName())
              .append("] after [").append(employeeAfterUpdate.getLastName()).append("]. ");
        }

        if (!Objects.equals(employeeBeforeUpdate.getDescription(), employeeAfterUpdate.getDescription())) {
            sb.append("description: before [").append(employeeBeforeUpdate.getDescription())
              .append("] after [").append(employeeAfterUpdate.getDescription()).append("]. ");
        }

        if (!Objects.equals(employeeBeforeUpdate.getCharacteristics(), employeeAfterUpdate.getCharacteristics())) {
            sb.append("characteristics: before [").append(employeeBeforeUpdate.getCharacteristics())
              .append("] after [").append(employeeAfterUpdate.getCharacteristics()).append("]. ");
        }

        if (!Objects.equals(employeeBeforeUpdate.getContacts(), employeeAfterUpdate.getContacts())) {
            sb.append("contacts: before [").append(employeeBeforeUpdate.getContacts())
              .append("] after [").append(employeeAfterUpdate.getContacts()).append("]. ");
        }

        if (!Objects.equals(employeeBeforeUpdate.getJobType(), employeeAfterUpdate.getJobType())) {
            sb.append("jobType: before [").append(employeeBeforeUpdate.getJobType())
              .append("] after [").append(employeeAfterUpdate.getJobType()).append("]. ");
        }

        if (!Objects.equals(employeeBeforeUpdate.getPost(), employeeAfterUpdate.getPost())) {
            sb.append("post: before [").append(employeeBeforeUpdate.getPost())
              .append("] after [").append(employeeAfterUpdate.getPost()).append("]. ");
        }
        return sb.toString();
    }

}
