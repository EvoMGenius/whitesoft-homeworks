package com.evo.apatrios.models.employeeKinds;

import com.evo.apatrios.models.roles.Role;

import java.math.BigDecimal;

public abstract class Employee  {
    private String firstName;
    private String lastName;
    private Role role;
    private BigDecimal salary;
    private Integer workingHours;
    private Integer workedHours;
    private Integer vacationInDays;
}
