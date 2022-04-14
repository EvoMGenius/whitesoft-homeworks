package com.evo.apatrios.models.employeeKinds;

public class CommonEmployee extends Employee implements com.evo.apatrios.models.roles.CommonEmployee {
    @Override
    public void doJob(Integer hours) {
        setWorkedHours(getWorkingHours()+hours);
        if (getWorkedHours() >= getWorkingHours()){
            salaryPaying();
        }
    }
}
