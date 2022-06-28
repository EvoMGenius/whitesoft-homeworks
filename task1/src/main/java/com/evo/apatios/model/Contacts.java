package com.evo.apatios.model;

import lombok.Data;

import javax.persistence.Embeddable;

@Data
@Embeddable
public class Contacts {
    private String phone;
    private String email;
    private String workEmail;
}
