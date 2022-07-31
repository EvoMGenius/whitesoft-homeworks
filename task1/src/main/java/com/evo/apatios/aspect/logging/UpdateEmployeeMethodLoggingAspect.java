package com.evo.apatios.aspect.logging;

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

    @Pointcut("@annotation(com.evo.apatios.aspect.logging.annotation.LogUpdateMethod)")
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

        if (!Objects.equals(employeeBeforeUpdate.getLastName(), employeeAfterUpdate.getFirstName())) {
            sb.append(getUpdateField(employeeBeforeUpdate, employeeAfterUpdate, "firstName"));
        }
        if (!Objects.equals(employeeBeforeUpdate.getLastName(), employeeAfterUpdate.getLastName())) {
            sb.append(getUpdateField(employeeBeforeUpdate, employeeAfterUpdate, "lastName"));
        }
        if (!Objects.equals(employeeBeforeUpdate.getDescription(), employeeAfterUpdate.getDescription())) {
            sb.append(getUpdateField(employeeBeforeUpdate, employeeAfterUpdate, "description"));
        }
        if (!Objects.equals(employeeBeforeUpdate.getCharacteristics(), employeeAfterUpdate.getCharacteristics())) {
            sb.append(getUpdateField(employeeBeforeUpdate, employeeAfterUpdate, "characteristics"));
        }
        if (!Objects.equals(employeeBeforeUpdate.getContacts(), employeeAfterUpdate.getContacts())) {
            sb.append(getUpdateField(employeeBeforeUpdate, employeeAfterUpdate, "contacts"));
        }
        if (!Objects.equals(employeeBeforeUpdate.getJobType(), employeeAfterUpdate.getJobType())) {
            sb.append(getUpdateField(employeeBeforeUpdate, employeeAfterUpdate, "jobType"));
        }
        if (!Objects.equals(employeeBeforeUpdate.getPost(), employeeAfterUpdate.getPost())) {
            sb.append(getUpdateField(employeeBeforeUpdate, employeeAfterUpdate, "post"));
        }
        return sb.toString();
    }

    private String getUpdateField(Employee employeeBeforeUpdate, UpdateEmployeeArgument employeeAfterUpdate, String fieldName) {
        if (fieldName.equals("firstName")) {
            return fieldName + ": before [" + employeeBeforeUpdate.getFirstName() + "] after [" + employeeAfterUpdate.getFirstName() + "]. ";
        }
        if (fieldName.equals("lastName")) {
            return fieldName + ": before [" + employeeBeforeUpdate.getLastName() + "] after [" + employeeAfterUpdate.getLastName() + "]. ";
        }
        if (fieldName.equals("description")) {
            return fieldName + ": before [" + employeeBeforeUpdate.getDescription() + "] after [" + employeeAfterUpdate.getDescription() + "]. ";
        }
        if (fieldName.equals("characteristics")) {
            return fieldName + ": before [" + employeeBeforeUpdate.getCharacteristics() + "] after [" + employeeAfterUpdate.getCharacteristics() + "]. ";
        }
        if (fieldName.equals("contacts")) {
            return fieldName + ": before [" + employeeBeforeUpdate.getContacts() + "] after [" + employeeAfterUpdate.getContacts() + "]. ";
        }
        if (fieldName.equals("jobType")) {
            return fieldName + ": before [" + employeeBeforeUpdate.getJobType() + "] after [" + employeeAfterUpdate.getJobType() + "]. ";
        }
        if (fieldName.equals("post")) {
            return fieldName + ": before [" + employeeBeforeUpdate.getPost() + "] after [" + employeeAfterUpdate.getPost() + "]. ";
        }
        return "";
    }

}
