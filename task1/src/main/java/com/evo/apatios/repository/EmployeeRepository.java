package com.evo.apatios.repository;

import com.evo.apatios.exception.NotFoundEmployeeException;
import com.evo.apatios.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class EmployeeRepository {

    private final List<Employee> employees = new ArrayList<>();

    public Employee save(Employee employee){
        Optional<Employee> optionalOfExistedEmployee = employees.stream().filter(empl ->
                empl.getFirstName().toLowerCase().equals(employee.getFirstName().toLowerCase()))
                .findFirst();
        if(optionalOfExistedEmployee.isPresent()){
            Employee existedEmployee = optionalOfExistedEmployee.get();
            int i = employees.indexOf(existedEmployee);
            employee.setId(existedEmployee.getId());
            employees.add(i,employee);
            return employee;
        }
        employee.setId(UUID.randomUUID());
        employees.add(employee);
        return employee;
    }

    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }

    public void deleteById(UUID id) {
        Optional<Employee> first = employees.stream().filter(employee -> employee.getId().equals(id)).findFirst();
        first.ifPresent(employees::remove);
    }

    public Optional<Employee> findById(UUID id) {
        return employees.stream().filter(employee -> employee.getId().equals(id)).findFirst();
    }
}
