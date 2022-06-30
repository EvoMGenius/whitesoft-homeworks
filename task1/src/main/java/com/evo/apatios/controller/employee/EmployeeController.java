package com.evo.apatios.controller.employee;

import com.evo.apatios.action.CreateEmployeeAction;
import com.evo.apatios.action.UpdateEmployeeAction;
import com.evo.apatios.controller.employee.mapper.EmployeeMapper;
import com.evo.apatios.dto.input.employee.CreateEmployeeDto;
import com.evo.apatios.dto.input.employee.UpdateEmployeeDto;
import com.evo.apatios.dto.output.employee.EmployeeDto;
import com.evo.apatios.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import com.evo.apatios.service.employee.EmployeeService;
import com.evo.apatios.service.params.SearchParams;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController { //todo добавить логирование обновление пользователя и фиксировать обращения по апи. *фиксация того с какого айпи пишут. ** вкл/выкл логирование в настройках через параметр.

    private final EmployeeService employeeService;
    private final CreateEmployeeAction createEmployeeAction;
    private final EmployeeMapper employeeMapper;
    private final UpdateEmployeeAction updateEmployeeAction;

    @GetMapping("/list")
    public List<EmployeeDto> findAllEmployees(SearchParams searchParams){
        return employeeService.getEmployeeList(searchParams).stream()
                .map(employeeMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EmployeeDto findById(@PathVariable UUID id){
        Employee employee = employeeService.getExisting(id);
        return employeeMapper.entityToDto(employee);
    }
    @PostMapping
    public EmployeeDto create(@RequestBody CreateEmployeeDto employeeDto){
        Employee createdEmployee = createEmployeeAction.execute(
                employeeMapper.createDtoToArgument(employeeDto));
        return employeeMapper.entityToDto(createdEmployee);
    }

    @PutMapping
    public EmployeeDto update(@RequestBody UpdateEmployeeDto employeeDto){
        Employee updatedEmployee = updateEmployeeAction.execute(
                employeeMapper.updateDtoToArgument(employeeDto));
        return employeeMapper.entityToDto(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id){
        employeeService.deleteById(id);
    }
}
