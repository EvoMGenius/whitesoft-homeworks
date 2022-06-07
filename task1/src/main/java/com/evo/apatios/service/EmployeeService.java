package com.evo.apatios.service;

import com.evo.apatios.argument.CreationEmployeeArgument;
import com.evo.apatios.argument.CreationEmployeeArgumentForService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.evo.apatios.repository.EmployeeRepository;
import com.evo.apatios.model.Employee;
import com.evo.apatios.service.utils.SearchParams;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    private final ObjectMapper mapper;

    @Autowired
    public EmployeeService(EmployeeRepository employees, ObjectMapper mapper) {
        this.repository = employees;
        this.mapper = mapper;
    }

    public Employee create(CreationEmployeeArgumentForService employee){
        Employee save = repository.save(mapper.convertValue(employee, Employee.class));
        return save;
    }

    public List<Employee> getEmployeesWithSearchParams(SearchParams params) {
        List<Predicate<Employee>> predicates = new ArrayList<>();
        List<Employee> employees = this.repository.findAll();
        if(params.getFirstName()!=null){
            predicates.add(employee -> employee.getFirstName().toLowerCase().contains(params.getFirstName().toLowerCase()));
        }
        if(params.getLastName()!=null){
            predicates.add(employee ->  employee.getLastName().toLowerCase().contains(params.getLastName().toLowerCase()));
        }
        if(params.getPostId()!=null){
            predicates.add(employee -> employee.getPost().getId().equals(params.getPostId()));
        }

        return employees.stream()
                .filter(predicates.stream().reduce(x->true , Predicate::and))
                .sorted(Comparator.comparing(Employee::getFirstName)
                        .thenComparing(Employee::getLastName))
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployees(){
        return repository.findAll();
    }
}
