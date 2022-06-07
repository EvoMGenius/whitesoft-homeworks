package com.evo.apatios.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Employee {

    private String firstName;
    private String lastName;
    private String description;
    private List<String> characteristics;
    private Post post;


    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", description='" + description + '\'' +
                ", characteristics=" + characteristics +
                ", post=" + post.getName() +
                '}';
    }
}