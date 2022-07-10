package com.evo.apatios.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Employee {

    @Id
    @GeneratedValue
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

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return id != null &&
                id.equals(employee.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, post);
    }
}