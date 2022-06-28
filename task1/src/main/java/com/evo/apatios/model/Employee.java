package com.evo.apatios.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String firstName;
    private String lastName;
    private String description;
    @ManyToOne
    @JoinColumn(name = "fk_post")
    private Post post;

    @Embedded
    private Contacts contacts;
    @ElementCollection
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