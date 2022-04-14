package com.evo.apatrios.models.employeeKinds;

import com.evo.apatrios.models.roles.Role;

import java.math.BigDecimal;

public abstract class Employee  {
    protected String firstName;
    protected String lastName;
    protected Role role;
    protected BigDecimal salary;
    protected BigDecimal balance;
    protected Integer workingHours;
    protected Integer workedHours;
    protected Integer vacationInDays;


    protected Employee() {
    }

    public void changeSalary(BigDecimal changeValue){
        this.salary= this.salary.add(changeValue);
    }

    public void salaryPaying(){
        balance = balance.add(salary.multiply(BigDecimal.valueOf(workedHours)));
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Integer getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Integer workingHours) {
        this.workingHours = workingHours;
    }

    public Integer getWorkedHours() {
        return workedHours;
    }

    public void setWorkedHours(Integer workedHours) {
        this.workedHours = workedHours;
    }

    public Integer getVacationInDays() {
        return vacationInDays;
    }

    public void setVacationInDays(Integer vacationInDays) {
        this.vacationInDays = vacationInDays;
    }


}
