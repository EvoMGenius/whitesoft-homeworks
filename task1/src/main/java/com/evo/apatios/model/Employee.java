package com.evo.apatios.model;

import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class Employee {

    private UUID id;

    private String firstName;
    private String lastName;
    private String description;
    private Post post;

    private Contacts contacts;
    private List<String> characteristics;
    private JobType jobType;


    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", description='" + description + '\'' +
                ", post=" + post.getName() +
                ", contacts=" + contacts +
                ", characteristics=" + characteristics +
                ", jobType=" + jobType +
                '}';
    }
}