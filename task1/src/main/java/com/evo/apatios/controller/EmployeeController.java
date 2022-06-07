package com.evo.apatios.controller;

import com.evo.apatios.action.AddEmployeeAction;
import com.evo.apatios.argument.CreationEmployeeArgument;
import com.evo.apatios.mapper.EmployeeMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.evo.apatios.dto.EmployeeDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.evo.apatios.service.EmployeeService;
import com.evo.apatios.service.utils.SearchParams;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AddEmployeeAction addEmployeeAction;

    private final ObjectMapper objectMapper;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, AddEmployeeAction addEmployeeAction, ObjectMapper objectMapper, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.addEmployeeAction = addEmployeeAction;
        this.objectMapper = objectMapper;
        this.employeeMapper = employeeMapper;
    }
    @GetMapping()
    public List<EmployeeDto> findAllEmployees(SearchParams searchParams){
        return employeeService.getEmployeesWithSearchParams(searchParams).stream()
                .map(employee -> objectMapper.convertValue(employee, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    @PostMapping
    public EmployeeDto create(@RequestBody EmployeeDto employeeDto){
        return employeeMapper.entityToDto(
                addEmployeeAction.execute(
                        objectMapper.convertValue(employeeDto, CreationEmployeeArgument.class)
                ));
    }
}
