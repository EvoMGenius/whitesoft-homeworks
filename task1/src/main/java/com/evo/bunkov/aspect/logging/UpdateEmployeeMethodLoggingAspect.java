package com.evo.bunkov.aspect.logging;

import com.evo.bunkov.aspect.logging.dto.LogUpdateDto;
import com.evo.bunkov.feing.LoggerServiceFeingClient;
import com.evo.bunkov.model.Employee;
import com.evo.bunkov.service.argument.employee.UpdateEmployeeArgument;
import com.evo.bunkov.service.employee.EmployeeService;
import com.mysema.commons.lang.Pair;
import lombok.AllArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Component
@Aspect
//@Slf4j
@AllArgsConstructor
@ConditionalOnProperty(prefix = "logger", name = "update")
public class UpdateEmployeeMethodLoggingAspect {

    private final EmployeeService employeeService;

    private final LoggerServiceFeingClient loggerFieng;

    @Pointcut("@annotation(com.evo.bunkov.aspect.logging.annotation.LogUpdateMethod)")
    public void annotationPointcut() {}

    @Before(value = "annotationPointcut() && args(employee)",
            argNames = "employee")
    public void saveLog(UpdateEmployeeArgument employee) {
        Employee existedEmployee = employeeService.getExisting(employee.getId());
        loggerFieng.logUpdate(getUpdatedFields(existedEmployee, employee));
    }

    private LogUpdateDto getUpdatedFields(Employee employeeBeforeUpdate, UpdateEmployeeArgument employeeAfterUpdate) {
        UUID id = employeeBeforeUpdate.getId();

        Map<String, Pair<String, String>> fieldMap = new HashMap<>();

        fieldMap.put("firstName", getUpdateField(employeeBeforeUpdate.getFirstName(), employeeAfterUpdate.getFirstName()));
        fieldMap.put("lastName", getUpdateField(employeeBeforeUpdate.getLastName(), employeeAfterUpdate.getLastName()));
        fieldMap.put("post", getUpdateField(employeeBeforeUpdate.getPost(), employeeAfterUpdate.getPost()));
        fieldMap.put("jobType", getUpdateField(employeeBeforeUpdate.getJobType(), employeeAfterUpdate.getJobType()));
        fieldMap.put("contacts", getUpdateField(employeeBeforeUpdate.getContacts(), employeeAfterUpdate.getContacts()));
        fieldMap.put("description", getUpdateField(employeeBeforeUpdate.getDescription(), employeeAfterUpdate.getDescription()));
        fieldMap.put("characteristics", getUpdateField(employeeBeforeUpdate.getCharacteristics(), employeeAfterUpdate.getCharacteristics()));

        Map<String, Pair<String, String>> updatedFieldMap = new HashMap<>();

        fieldMap.entrySet().stream()
                     .filter(stringPairEntry ->
                                     !stringPairEntry.getValue().equals(new Pair<>("", "")))
                     .forEach(stringPairEntry ->
                                      updatedFieldMap.put(stringPairEntry.getKey(), stringPairEntry.getValue()));
        return LogUpdateDto.builder()
                           .updatedEmployeeId(id)
                           .updatedFields(updatedFieldMap)
                           .build();
    }

    private Pair<String, String> getUpdateField(Object currentValue, Object newValue) {
        return Objects.equals(currentValue, newValue)
               ? new Pair<>("", "")
               : new Pair<>(currentValue.toString(), newValue.toString());
    }
}
