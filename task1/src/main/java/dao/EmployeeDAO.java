package dao;

import models.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    private final List<Employee> employees = new ArrayList<>();


    public void add(Employee employee) {
        if(employees.contains(employee)){
            throw new RuntimeException("This employee is already exists");
        }
        employees.add(employee);
    }

    public List<Employee> getEmployees() {
        return employees;
    }
}
