package com.evo.apatios.controller.employee;

import com.evo.apatios.action.CreationEmployeeAction;
import com.evo.apatios.action.UpdatingEmployeeAction;
import com.evo.apatios.action.argument.UpdatingEmployeeActionArgument;
import com.evo.apatios.controller.employee.mapper.EmployeeMapper;
import com.evo.apatios.dto.transfer.New;
import com.evo.apatios.dto.transfer.Update;
import com.evo.apatios.exception.NotFoundEmployeeException;
import com.evo.apatios.dto.EmployeeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.evo.apatios.service.employee.EmployeeService;
import com.evo.apatios.service.params.SearchParams;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final CreationEmployeeAction creationEmployeeAction;
    private final EmployeeMapper employeeMapper;
    private final UpdatingEmployeeAction updatingEmployeeAction;

    @GetMapping("/all")
    public List<EmployeeDto> findAllEmployees(SearchParams searchParams){
        return employeeService.getEmployeeList(searchParams).stream()
                .map(employeeMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EmployeeDto findById(@PathVariable UUID id){
        return employeeMapper.entityToDto(employeeService.findById(id).orElseThrow(NotFoundEmployeeException::new));
    }
    @PostMapping
    public EmployeeDto create(@RequestBody
                                  @Validated(New.class) EmployeeDto employeeDto){
        return employeeMapper.entityToDto(
                creationEmployeeAction.execute(
                        employeeMapper.dtoToCreationActionArgument(employeeDto)
                ));
    }

    @PutMapping
    public EmployeeDto update(@RequestBody
                                  @Validated(Update.class) EmployeeDto employeeDto){
        return employeeMapper.entityToDto(
                updatingEmployeeAction.execute(
                        employeeMapper.dtoToUpdatingActionArgument(employeeDto))
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable UUID id){
        employeeService.deleteById(id);
    }
}
