package com.evo.apatrios.models.roles;

public class Head implements Role{
    public String doReport(){
        return "Какая-нибудь отчетная информация";
    }

    @Override
    public void doJob(Integer hours) {
        doReport();
    }
}
