package services;

import dao.EmployeeDAO;
import models.Employee;
import models.SearchParams;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EmployeeService {

    private final EmployeeDAO employeeDAO;

    public EmployeeService(EmployeeDAO employees) {
        this.employeeDAO = employees;
    }

    public void addEmployee(Employee employee){
        employeeDAO.add(employee);
    }

    public List<Employee> getEmployeesWithSearchParams(SearchParams params) {
        List<Employee> employees = this.employeeDAO.getEmployees();
        Predicate<Employee> predicateFirstName = employee -> true;
        Predicate<Employee> predicateLastName= employee -> true;
        Predicate<Employee> predicateUUID = employee -> true;
        if(params.getFirstName()!=null){
            predicateFirstName = employee -> employee.getFirstName().toLowerCase().contains(params.getFirstName().toLowerCase());
        }
        if(params.getLastName()!=null){
            predicateLastName = employee ->  employee.getLastName().toLowerCase().contains(params.getLastName().toLowerCase());
        }
        if(params.getPostId()!=null){
            predicateUUID = employee -> employee.getPost().getId().equals(params.getPostId());
        }

        return employees.stream()
                .filter(predicateFirstName)
                .filter(predicateLastName)
                .filter(predicateUUID)
                .sorted(Comparator.comparing(Employee::getFirstName)
                        .thenComparing(Employee::getLastName))
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployees(){
        return employeeDAO.getEmployees();
    }
}
