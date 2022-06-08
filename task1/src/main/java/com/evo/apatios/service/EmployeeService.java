package com.evo.apatios.service;

import com.evo.apatios.exception.CreationEmployeeException;
import com.evo.apatios.exception.UpdatingEmployeeException;
import com.evo.apatios.util.Guard;
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
        Guard.check(employee.getId()==null, "Employee Id must be null", CreationEmployeeException::new);
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

    public void deleteById(UUID id){
        repository.deleteById(id);
    }

    public Employee update(UpdateEmployeeArgument employee){
        Guard.check(employee.getId()!=null, "Employee Id must NOT be null", UpdatingEmployeeException::new);
        return repository.update(Employee.builder()
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



    public List<Employee> getEmployees(){
        return repository.findAll();
    }


    public Optional<Employee> findById(UUID id) {
        return repository.findById(id);
    }
}
