package argument;

import lombok.Data;
import model.Post;

import java.util.List;

@Data
public class CreationEmployeeArgument {
    private String firstName;
    private String lastName;
    private String description;
    private List<String> characteristics;
    private String postName;
}
