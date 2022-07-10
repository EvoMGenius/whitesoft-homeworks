package com.evo.apatios.controller.employee;

import com.evo.apatios.action.CreateEmployeeAction;
import com.evo.apatios.action.UpdateEmployeeAction;
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

import static com.evo.apatios.controller.employee.mapper.EmployeeMapper.EMPLOYEE_MAPPER;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CreateEmployeeAction createEmployeeAction;
    private final UpdateEmployeeAction updateEmployeeAction;

    @GetMapping("/list")
    public List<EmployeeDto> findAllEmployees(SearchParams searchParams){
        return employeeService.getEmployeeList(searchParams).stream()
                .map(EMPLOYEE_MAPPER::entityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EmployeeDto findById(@PathVariable UUID id){
        Employee employee = employeeService.getExisting(id);
        return EMPLOYEE_MAPPER.entityToDto(employee);
    }

    @PostMapping
    public EmployeeDto create(@RequestBody CreateEmployeeDto employeeDto){
        Employee createdEmployee = createEmployeeAction.execute(
                EMPLOYEE_MAPPER.createDtoToArgument(employeeDto));
        return EMPLOYEE_MAPPER.entityToDto(createdEmployee);
    }

    @PutMapping("/{id}")
    public EmployeeDto update(@PathVariable UUID id,
                              @RequestBody UpdateEmployeeDto employeeDto){
        Employee updatedEmployee = updateEmployeeAction.execute( id,
                EMPLOYEE_MAPPER.updateDtoToArgument(employeeDto));
        return EMPLOYEE_MAPPER.entityToDto(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id){
        employeeService.deleteById(id);
    }
}
