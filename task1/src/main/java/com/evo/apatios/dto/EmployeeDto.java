package com.evo.apatios.dto;

import com.evo.apatios.model.Post;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private String description;
    private List<String> characteristics;
    private UUID postId;
}
