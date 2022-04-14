package com.evo.apatrios.models.employeeKinds;

import com.evo.apatrios.models.roles.Role;

import java.math.BigDecimal;

public class CommonEmployee extends Employee implements com.evo.apatrios.models.roles.entitiesRoles.CommonEmployee {
    private CommonEmployee(){}
    @Override
    public void doJob(Integer hours) {
        setWorkedHours(getWorkingHours()+hours);
        if (getWorkedHours() >= getWorkingHours()){
            salaryPaying();
        }
    }

    public void doJobByRole(Integer hours){
        role.doJob(hours);
    }
    @Override
    public void doOvertimeJob(Integer hours) {
        doJob(hours);
    }

    public static CommonEmployee.Builder newBuilder(){
        return new CommonEmployee().new Builder();
    }

    public class Builder {
        private Builder() {

        }

        public CommonEmployee.Builder setFirstName(String firstName) {
            CommonEmployee.this.firstName = firstName;
            return this;
        }

        public CommonEmployee.Builder setLastName(String lastName) {
            CommonEmployee.this.lastName = lastName;
            return this;
        }

        public CommonEmployee.Builder setRole(Role role) {
            CommonEmployee.this.role = role;
            return this;
        }

        public CommonEmployee.Builder setSalary(BigDecimal salary) {
            CommonEmployee.this.salary = salary;
            return this;
        }

        public CommonEmployee.Builder setBalance(BigDecimal balance) {
            CommonEmployee.this.balance = balance;
            return this;
        }

        public CommonEmployee.Builder setWorkingHours(Integer workingHours) {
            CommonEmployee.this.workingHours = workingHours;
            return this;
        }

        public CommonEmployee.Builder setWorkedHours(Integer workedHours) {
            CommonEmployee.this.workedHours = workedHours;
            return this;
        }

        public CommonEmployee.Builder setVacationInDays(Integer vacationInDays) {
            CommonEmployee.this.vacationInDays = vacationInDays;
            return this;
        }

        public CommonEmployee build() {
            if (CommonEmployee.this.workedHours == null) {
                CommonEmployee.this.workedHours = 0;
            }
            if (CommonEmployee.this.firstName == null || CommonEmployee.this.lastName == null
                    || CommonEmployee.this.role == null || CommonEmployee.this.salary == null
                    || CommonEmployee.this.workingHours == null) {
                throw new RuntimeException("Не все обязательные поля объекта заполнены. Заполните все");
            }
            if (CommonEmployee.this.balance == null) {
                CommonEmployee.this.balance = BigDecimal.valueOf(0);
            }
            return CommonEmployee.this;
        }
    }
}
