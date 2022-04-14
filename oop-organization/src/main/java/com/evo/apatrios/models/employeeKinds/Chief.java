package com.evo.apatrios.models.employeeKinds;

public class Chief extends Employee implements com.evo.apatrios.models.roles.Chief {
    @Override
    public void doJob(Integer hours) {
        setWorkedHours(getWorkingHours()+hours);
        if (getWorkedHours() >= getWorkingHours()){
            salaryPaying();
        }
    }
}
