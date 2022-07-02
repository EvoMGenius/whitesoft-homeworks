package com.evo.apatios.model;

import com.evo.apatios.service.argument.employee.UpdateEmployeeArgument;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

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
public class Employee {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
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

    public void setAllFields(UpdateEmployeeArgument employee) {
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.description = employee.getDescription();
        this.post = employee.getPost();
        this.contacts = employee.getContacts();
        this.characteristics = employee.getCharacteristics();
        this.jobType = employee.getJobType();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o==null) return false;
        if(getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id,employee.getId()) && Objects.equals(post, employee.getPost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, post);
    }
}