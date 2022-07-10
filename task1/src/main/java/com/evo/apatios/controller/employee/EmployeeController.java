package com.evo.apatios.controller.employee;

import com.evo.apatios.action.CreateEmployeeAction;
import com.evo.apatios.action.UpdateEmployeeAction;
import com.evo.apatios.dto.input.employee.CreateEmployeeDto;
import com.evo.apatios.dto.input.employee.UpdateEmployeeDto;
import com.evo.apatios.dto.output.employee.EmployeeDto;
import com.evo.apatios.model.Employee;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.evo.apatios.service.employee.EmployeeService;
import com.evo.apatios.service.params.SearchParams;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.evo.apatios.controller.employee.mapper.EmployeeMapper.EMPLOYEE_MAPPER;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
@Api(tags = "employee-controller")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CreateEmployeeAction createEmployeeAction;
    private final UpdateEmployeeAction updateEmployeeAction;

    @ApiOperation(value = "Вывод списка сотрудников.",notes = "Найти сотрудников по имени, фамилии или id должности.")
    @GetMapping("/list")
    public List<EmployeeDto> findAllEmployees(
            @ApiParam(name ="Параметры поиска" , example = "firstName=Иван&lastName=Иванов&postId=4085e25e-6e6c-4cf1-8949-63c4175bf168",
                    allowEmptyValue = true)
            SearchParams searchParams){
        return employeeService.getEmployeeList(searchParams).stream()
                .map(EMPLOYEE_MAPPER::entityToDto)
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Найти сотрудника по id.",notes = "Получение одного сотрудника по id.", response = EmployeeDto.class)
    @GetMapping("/{id}")
    public EmployeeDto findById(@PathVariable UUID id){
        Employee employee = employeeService.getExisting(id);
        return EMPLOYEE_MAPPER.entityToDto(employee);
    }

    @PostMapping
    @ApiOperation(value ="Создать сотрудника.", notes = "Создать нового сотрудника.", response = EmployeeDto.class)
    public EmployeeDto create(@RequestBody CreateEmployeeDto employeeDto){
        Employee createdEmployee = createEmployeeAction.execute(
                EMPLOYEE_MAPPER.createDtoToArgument(employeeDto));
        return EMPLOYEE_MAPPER.entityToDto(createdEmployee);
    }

    @PutMapping("/{id}")
    @ApiOperation(value ="Изменить сотрудника.", notes = "Редактировать существующего сотрудника.", response = EmployeeDto.class)
    public EmployeeDto update(@PathVariable UUID id,
                              @RequestBody UpdateEmployeeDto employeeDto){
        Employee updatedEmployee = updateEmployeeAction.execute( id,
                EMPLOYEE_MAPPER.updateDtoToArgument(employeeDto));
        return EMPLOYEE_MAPPER.entityToDto(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удалить сотрудника по id.")
    public void deleteById(@PathVariable UUID id){
        employeeService.deleteById(id);
    }
}
