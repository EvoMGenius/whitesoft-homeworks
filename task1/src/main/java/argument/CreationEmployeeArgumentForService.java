package argument;

import lombok.Builder;
import lombok.Data;
import model.Post;

import java.util.List;
@Data
@Builder
public class CreationEmployeeArgumentForService {
    private String firstName;
    private String lastName;
    private String description;
    private List<String> characteristics;
    private Post post;
}
