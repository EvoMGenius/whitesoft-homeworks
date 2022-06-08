package com.evo.apatios.service;

import com.evo.apatios.util.Guard;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.evo.apatios.repository.EmployeeRepository;
import com.evo.apatios.model.Employee;
import com.evo.apatios.service.params.SearchParams;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    @Autowired
    public EmployeeService(EmployeeRepository employees) {
        this.repository = employees;
    }

    public Employee create(CreationEmployeeArgument employee){
        return repository.save(Employee.builder()
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .characteristics(employee.getCharacteristics())
                .description(employee.getDescription())
                .post(employee.getPost())
                .build());
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
