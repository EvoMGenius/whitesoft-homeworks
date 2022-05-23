package models;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
//@ToString
@Builder
public class Employee {

    protected String firstName;
    protected String lastName;
    protected String description;
    protected List<String> characteristics;
    protected Post post;


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