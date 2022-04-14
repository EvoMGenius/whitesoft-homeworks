package com.evo.apatrios.models.roles;

public class Chief extends Head{
    public String doFinanceReport(){
        return "Какой-то финансовый отчет";
    }

    @Override
    public void doJob(Integer hours) {
        super.doJob(hours);
        doFinanceReport();
    }
}
