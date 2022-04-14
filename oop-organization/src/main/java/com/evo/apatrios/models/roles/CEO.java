package com.evo.apatrios.models.roles;

public class CEO extends Chief{
    public String doGlobalReport(){
        return "Какой-то глобальный отчет";
    }

    @Override
    public void doJob(Integer hours) {
        super.doJob(hours);
        doGlobalReport();
    }
}
