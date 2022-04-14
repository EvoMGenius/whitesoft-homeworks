package com.evo.apatrios.models.employeeKinds;

import com.evo.apatrios.models.roles.Role;
import com.evo.apatrios.models.utils.Criteria;

import java.math.BigDecimal;

public class Programmer extends Employee implements com.evo.apatrios.models.roles.Programmer {
    @Override
    public void doJob(Integer hours) {
        setWorkedHours(getWorkingHours()+hours);
        if (getWorkedHours() >= getWorkingHours()){
            salaryPaying();
        }
    }

    @Override
    public String develop(Criteria criteria, BigDecimal price, Integer hoursForDeveloping) {
        if (criteria.getCriteria().size() > 7) {
            changeSalary(price.add(BigDecimal.valueOf(price.doubleValue()*0.02*hoursForDeveloping)));

            return String.format("Требую премии. Скажи спасибо что не беру надбавку за %s дней работы", hoursForDeveloping);
        }
        changeSalary(price);
        return String.format("Дело сделано за %d дней", hoursForDeveloping);
    }
    public static Builder newBuilder(){
        return new Programmer().new Builder();
    }

    public class Builder{
        private Builder(){

        }

        public Builder setFirstName(String firstName){
            Programmer.this.firstName = firstName;
            return this;
        }
        public Builder setLastName(String lastName){
            Programmer.this.lastName = lastName;
            return this;
        }
        public Builder setRole(Role role){
            Programmer.this.role = role;
            return this;
        }
        public Builder setSalary(BigDecimal salary){
            Programmer.this.salary = salary;
            return this;
        }
        public Builder setBalance(BigDecimal balance){
            Programmer.this.balance = balance;
            return this;
        }
        public Builder setWorkingHours(Integer workingHours){
            Programmer.this.workingHours = workingHours;
            return this;
        }
        public Builder setWorkedHours(Integer workedHours){
            Programmer.this.workedHours = workedHours;
            return this;
        }
        public Builder setVacationInDays(Integer vacationInDays){
            Programmer.this.vacationInDays = vacationInDays;
            return this;
        }
        public Programmer build(){
            if(Programmer.this.workedHours == null){
                Programmer.this.workedHours = 0;
            }
            if(Programmer.this.firstName==null || Programmer.this.lastName == null
                    || Programmer.this.role == null || Programmer.this.salary == null
                    || Programmer.this.workingHours == null){
                throw new RuntimeException("Не все обязательные поля объекта заполнены. Заполните все");
            }
            if(Programmer.this.balance==null){
                Programmer.this.balance = BigDecimal.valueOf(0);
            }
            return Programmer.this;
        }
    }
}
