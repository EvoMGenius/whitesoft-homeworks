package com.evo.apatios.repository;

import com.evo.apatios.exception.CreationEmployeeException;
import com.evo.apatios.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {

    private final List<Employee> employees = new ArrayList<>();

    public Employee save(Employee employee) {
        if(employees.contains(employee)){
            throw new CreationEmployeeException("This employee is already exists");
        }
        employees.add(employee);
        return employee;
    }

    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }
}
