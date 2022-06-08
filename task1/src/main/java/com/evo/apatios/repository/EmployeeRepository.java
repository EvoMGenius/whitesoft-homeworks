package com.evo.apatios.repository;

import com.evo.apatios.exception.CreationEmployeeException;
import com.evo.apatios.model.Employee;
import com.evo.apatios.util.Guard;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
}
