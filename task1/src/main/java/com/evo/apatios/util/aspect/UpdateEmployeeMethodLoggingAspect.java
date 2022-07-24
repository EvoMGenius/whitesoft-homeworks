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

    @Pointcut("execution(public * com.evo.apatios.service.employee.EmployeeService.update(..))")
    public void updateMethodsPointcut() {}

    @Before(value = "updateMethodsPointcut() && args(employee)",
            argNames = "employee")
    public void saveLog(UpdateEmployeeArgument employee) {
        log.info("Updating employee with id : {}", employee.getId());
        Employee existedEmployee = employeeService.getExisting(employee.getId());
        logUpdatingFields(existedEmployee, employee);
    }

    private void logUpdatingFields(Employee employeeBeforeUpdate, UpdateEmployeeArgument employeeAfterUpdate) {

        if (!Objects.equals(employeeBeforeUpdate.getFirstName(), employeeAfterUpdate.getFirstName())) {
            log.info("firstName: before [{}] after [{}]", employeeBeforeUpdate.getFirstName(), employeeAfterUpdate.getFirstName());
        }

        if (!Objects.equals(employeeBeforeUpdate.getLastName(), employeeAfterUpdate.getLastName())) {
            log.info("lastName: before [{}] after [{}]", employeeBeforeUpdate.getLastName(), employeeAfterUpdate.getLastName());
        }

        if (!Objects.equals(employeeBeforeUpdate.getDescription(), employeeAfterUpdate.getDescription())) {
            log.info("description: before [{}] after [{}]", employeeBeforeUpdate.getDescription(), employeeAfterUpdate.getDescription());
        }

        if (!Objects.equals(employeeBeforeUpdate.getCharacteristics(), employeeAfterUpdate.getCharacteristics())) {
            log.info("characteristics: before [{}] after [{}]", employeeBeforeUpdate.getCharacteristics(), employeeAfterUpdate.getCharacteristics());
        }

        if (!Objects.equals(employeeBeforeUpdate.getContacts(), employeeAfterUpdate.getContacts())) {
            log.info("contacts: before [{}] after [{}]", employeeBeforeUpdate.getContacts(), employeeAfterUpdate.getContacts());
        }

        if (!Objects.equals(employeeBeforeUpdate.getJobType(), employeeAfterUpdate.getJobType())) {
            log.info("jobType: before [{}] after [{}]", employeeBeforeUpdate.getJobType(), employeeAfterUpdate.getJobType());
        }

        if (!Objects.equals(employeeBeforeUpdate.getPost(), employeeAfterUpdate.getPost())) {
            log.info("post: before [{}] after [{}]", employeeBeforeUpdate.getPost(), employeeAfterUpdate.getPost());
        }
    }

}
