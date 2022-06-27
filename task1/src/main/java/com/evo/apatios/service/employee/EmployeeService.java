package com.evo.apatios.service.employee;

import com.evo.apatios.repository.EmployeeRepository;
import com.evo.apatios.service.argument.CreationEmployeeAgrument;
import com.evo.apatios.service.argument.UpdatingEmployeeArgument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.evo.apatios.model.Employee;
import com.evo.apatios.service.params.SearchParams;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;

    public Employee create(CreationEmployeeAgrument employee){
        return repository.save(Employee.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .description(employee.getDescription())
                .post(employee.getPost())
                .contacts(employee.getContacts())
                .characteristics(employee.getCharacteristics())
                .jobType(employee.getJobType())
                .build());
    }

    public Employee update(UpdatingEmployeeArgument employee){
        return repository.save(Employee.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .description(employee.getDescription())
                .post(employee.getPost())
                .contacts(employee.getContacts())
                .characteristics(employee.getCharacteristics())
                .jobType(employee.getJobType())
                .build());
    }

    public List<Employee> getEmployeeList(SearchParams params) {
        Predicate<Employee> predicate = Objects::nonNull;
        List<Employee> employees = this.repository.findAll();
        if(params.getFirstName()!=null){
            predicate = predicate.and(employee -> employee.getFirstName().toLowerCase().contains(params.getFirstName().toLowerCase()));
        }
        if(params.getLastName()!=null){
            predicate = predicate.and(employee ->  employee.getLastName().toLowerCase().contains(params.getLastName().toLowerCase()));
        }
        if(params.getPostId()!=null){
            predicate = predicate.and(employee -> employee.getPost().getId().equals(params.getPostId()));
        }

        return employees.stream()
                .filter(predicate)
                .sorted(Comparator.comparing(Employee::getFirstName)
                        .thenComparing(Employee::getLastName))
                .collect(Collectors.toList());
    }

    public void deleteById(UUID id){
        repository.deleteById(id);
    }

    public Optional<Employee> findById(UUID id) {
        return repository.findById(id);
    }
}
