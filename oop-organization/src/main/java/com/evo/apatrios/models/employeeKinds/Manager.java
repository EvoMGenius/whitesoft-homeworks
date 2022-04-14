package com.evo.apatrios.models.employeeKinds;

import com.evo.apatrios.models.roles.Role;

import java.math.BigDecimal;

public class Manager extends Employee implements com.evo.apatrios.models.roles.entitiesRoles.Manager {
    private Manager(){}
    @Override
    public void doJob(Integer hours) {
        setWorkedHours(getWorkingHours()+hours);
        role.doJob(hours/4);
        if (getWorkedHours() >= getWorkingHours()){
            salaryPaying();
        }
    }

    public void doJobByRole(Integer hours){
        role.doJob(hours);
    }

    @Override
    public String manage() {
        return "Ну типа я менеджер, работаю с людишками";
    }

    public static Manager.Builder newBuilder(){
        return new Manager().new Builder();
    }

    public class Builder {
        private Builder() {

        }

        public Manager.Builder setFirstName(String firstName) {
            Manager.this.firstName = firstName;
            return this;
        }

        public Manager.Builder setLastName(String lastName) {
            Manager.this.lastName = lastName;
            return this;
        }

        public Manager.Builder setRole(Role role) {
            Manager.this.role = role;
            return this;
        }

        public Manager.Builder setSalary(BigDecimal salary) {
            Manager.this.salary = salary;
            return this;
        }

        public Manager.Builder setBalance(BigDecimal balance) {
            Manager.this.balance = balance;
            return this;
        }

        public Manager.Builder setWorkingHours(Integer workingHours) {
            Manager.this.workingHours = workingHours;
            return this;
        }

        public Manager.Builder setWorkedHours(Integer workedHours) {
            Manager.this.workedHours = workedHours;
            return this;
        }

        public Manager.Builder setVacationInDays(Integer vacationInDays) {
            Manager.this.vacationInDays = vacationInDays;
            return this;
        }

        public Manager build() {
            if (Manager.this.workedHours == null) {
                Manager.this.workedHours = 0;
            }
            if (Manager.this.firstName == null || Manager.this.lastName == null
                    || Manager.this.role == null || Manager.this.salary == null
                    || Manager.this.workingHours == null) {
                throw new RuntimeException("Не все обязательные поля объекта заполнены. Заполните все");
            }
            if (Manager.this.balance == null) {
                Manager.this.balance = BigDecimal.valueOf(0);
            }
            return Manager.this;
        }
    }
}
