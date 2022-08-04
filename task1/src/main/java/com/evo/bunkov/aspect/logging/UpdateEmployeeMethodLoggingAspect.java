package com.evo.bunkov.aspect.logging;

import com.evo.bunkov.model.Employee;
import com.evo.bunkov.service.argument.employee.UpdateEmployeeArgument;
import com.evo.bunkov.service.employee.EmployeeService;
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

    @Pointcut("@annotation(com.evo.bunkov.aspect.logging.annotation.LogUpdateMethod)")
    public void annotationPointcut() {}

    @Before(value = "annotationPointcut() && args(employee)",
            argNames = "employee")
    public void saveLog(UpdateEmployeeArgument employee) {
        Employee existedEmployee = employeeService.getExisting(employee.getId());
        log.info(String.format("Updating employee with id : %s, fields update : %s",
                 employee.getId(), getUpdatingFields(existedEmployee, employee)));
    }

    private String getUpdatingFields(Employee employeeBeforeUpdate, UpdateEmployeeArgument employeeAfterUpdate) {
        String sb = getUpdateField(employeeBeforeUpdate.getFirstName(), employeeAfterUpdate.getFirstName(), "firstName") +
                    getUpdateField(employeeBeforeUpdate.getLastName(), employeeAfterUpdate.getLastName(), "lastName") +
                    getUpdateField(employeeBeforeUpdate.getPost(), employeeAfterUpdate.getPost(), "post") +
                    getUpdateField(employeeBeforeUpdate.getJobType(), employeeAfterUpdate.getJobType(), "jobType") +
                    getUpdateField(employeeBeforeUpdate.getContacts(), employeeAfterUpdate.getContacts(), "contacts") +
                    getUpdateField(employeeBeforeUpdate.getDescription(), employeeAfterUpdate.getDescription(), "description") +
                    getUpdateField(employeeBeforeUpdate.getCharacteristics(), employeeAfterUpdate.getCharacteristics(), "characteristics");

        return sb;
    }

    private String getUpdateField(Object currentValue, Object newValue, String fieldName) {
        return Objects.equals(currentValue, newValue)
               ? ""
               : fieldName + ": before [" + currentValue + "] after [" + newValue + "]. ";
    }
}
