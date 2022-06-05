package dto;

import lombok.Data;
import model.Post;

import java.util.List;

@Data
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private String description;
    private List<String> characteristics;
    private Post post;
}
