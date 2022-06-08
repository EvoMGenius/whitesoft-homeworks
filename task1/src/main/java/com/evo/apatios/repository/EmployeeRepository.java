package com.evo.apatios.repository;

import com.evo.apatios.exception.CreationEmployeeException;
import com.evo.apatios.exception.NotFoundEmployeeException;
import com.evo.apatios.model.Employee;
import com.evo.apatios.util.Guard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class EmployeeRepository {

    private final List<Employee> employees = new ArrayList<>();

    public Employee save(Employee employee) {
        Guard.check(employees.contains(employee), String.format("This Employee - %s is already exists",employee),CreationEmployeeException::new);
        employees.add(employee);
        return employee;
    }

    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }

    public Optional<Employee> findById(UUID id){
        return employees.stream().filter(employee -> employee.getId().equals(id)).findFirst();
    }

    public void deleteById(UUID id){
        Employee employee = employees.stream().filter(emp -> emp.getId().equals(id)).findFirst().orElseThrow(() -> new NotFoundEmployeeException(""));
        employees.remove(employee);
    }

    public Employee update(Employee employee){
        Employee employee1 = employees.stream().filter(emp -> emp.getId().equals(employee.getId())).findFirst().orElseThrow(() -> new NotFoundEmployeeException(""));
        int index = employees.indexOf(employee1);
        employees.add(index, employee);
        return employee;
    }
}
