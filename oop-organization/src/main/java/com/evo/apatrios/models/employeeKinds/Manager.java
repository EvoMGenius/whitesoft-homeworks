package com.evo.apatrios.models.employeeKinds;

public class Manager extends Employee implements com.evo.apatrios.models.roles.Manager {
    @Override
    public void doJob(Integer hours) {
        setWorkedHours(getWorkingHours()+hours);
        if (getWorkedHours() >= getWorkingHours()){
            salaryPaying();
        }
    }

    @Override
    public String manage() {
        return "Ну типа я менеджер, работаю с людишками";
    }
}
