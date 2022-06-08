package com.evo.apatios.controller;

import com.evo.apatios.action.CreationEmployeeAction;
import com.evo.apatios.action.UpdateEmployeeAction;
import com.evo.apatios.exception.NotFoundEmployeeException;
import com.evo.apatios.mapper.EmployeeMapper;
import com.evo.apatios.dto.EmployeeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.evo.apatios.service.EmployeeService;
import com.evo.apatios.service.params.SearchParams;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CreationEmployeeAction creationEmployeeAction;
    private final UpdateEmployeeAction  updateEmployeeAction;
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeController(EmployeeService employeeService, CreationEmployeeAction creationEmployeeAction, UpdateEmployeeAction updateEmployeeAction, EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.creationEmployeeAction = creationEmployeeAction;
        this.updateEmployeeAction = updateEmployeeAction;
        this.employeeMapper = employeeMapper;
    }
    @GetMapping()
    public List<EmployeeDto> findAllEmployees(SearchParams searchParams){
        return employeeService.getEmployeesWithSearchParams(searchParams).stream()
                .map(employeeMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EmployeeDto findById(@PathVariable UUID id){
        return employeeMapper.entityToDto(employeeService.findById(id).orElseThrow(NotFoundEmployeeException::new));
    }
    @PostMapping
    public EmployeeDto create(@RequestBody EmployeeDto employeeDto){
        return employeeMapper.entityToDto(
                creationEmployeeAction.execute(
                        employeeMapper.dtoToCreationActionArgument(employeeDto)
                ));
    }

    @PutMapping
    public EmployeeDto update(@RequestBody EmployeeDto employeeDto){
        return employeeMapper.entityToDto(
                updateEmployeeAction.execute(
                        employeeMapper.dtoToUpdateActionArgument(employeeDto)
                )
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable UUID id){
        employeeService.deleteById(id);
    }
}
