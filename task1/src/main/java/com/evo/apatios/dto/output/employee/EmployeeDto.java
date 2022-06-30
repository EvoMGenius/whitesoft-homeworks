package com.evo.apatios.dto.output.employee;

import com.evo.apatios.model.Contacts;
import com.evo.apatios.model.JobType;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class EmployeeDto {

    private UUID id;

    private String firstName;
    private String lastName;
    private String description;
    private UUID postId;

    private Contacts contacts;
    private List<String> characteristics;
    private JobType jobType;
}
