package com.evo.apatios.argument;

import com.evo.apatios.model.Post;
import lombok.Builder;
import lombok.Data;

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
