package com.evo.apatios.argument;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CreationEmployeeActionArgument {
    private String firstName;
    private String lastName;
    private String description;
    private List<String> characteristics;
    private UUID postId;
}
