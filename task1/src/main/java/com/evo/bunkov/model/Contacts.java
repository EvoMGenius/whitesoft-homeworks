package com.evo.bunkov.model;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Embeddable
public class Contacts {
    private String phone;
    private String email;
    private String workEmail;
}
