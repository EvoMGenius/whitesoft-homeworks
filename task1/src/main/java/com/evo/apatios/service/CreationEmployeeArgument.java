package com.evo.apatios.service;

import com.evo.apatios.model.Post;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreationEmployeeArgument {
    private String firstName;
    private String lastName;
    private String description;
    private List<String> characteristics;
    private Post post;
}
