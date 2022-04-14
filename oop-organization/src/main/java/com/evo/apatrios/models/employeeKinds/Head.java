package com.evo.apatrios.models.employeeKinds;

public class Head extends Employee implements com.evo.apatrios.models.roles.Head {
    @Override
    public void doJob(Integer hours) {
        setWorkedHours(getWorkingHours()+hours);
        if (getWorkedHours() >= getWorkingHours()){
            salaryPaying();
        }
    }
}
