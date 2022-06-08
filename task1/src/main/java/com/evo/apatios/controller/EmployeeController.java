package com.evo.apatios.controller;

import com.evo.apatios.action.AddEmployeeAction;
import com.evo.apatios.argument.CreationEmployeeActionArgument;
import com.evo.apatios.mapper.EmployeeMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.evo.apatios.dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.evo.apatios.service.EmployeeService;
import com.evo.apatios.service.params.SearchParams;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AddEmployeeAction addEmployeeAction;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, AddEmployeeAction addEmployeeAction, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.addEmployeeAction = addEmployeeAction;
        this.employeeMapper = employeeMapper;
    }
    @GetMapping()
    public List<EmployeeDto> findAllEmployees(SearchParams searchParams){
        return employeeService.getEmployeesWithSearchParams(searchParams).stream()
                .map(employeeMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public EmployeeDto create(@RequestBody EmployeeDto employeeDto){
        return employeeMapper.entityToDto(
                addEmployeeAction.execute(
                        employeeMapper.dtoToCreationActionArgument(employeeDto)
                ));
    }
}
