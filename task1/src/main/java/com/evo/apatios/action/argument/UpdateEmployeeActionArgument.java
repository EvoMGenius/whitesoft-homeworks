package com.evo.apatios.action.argument;

import com.evo.apatios.model.Contacts;
import com.evo.apatios.model.JobType;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UpdateEmployeeActionArgument {

    private UUID id;

    private String firstName;
    private String lastName;
    private String description;
    private UUID postId;

    private Contacts contacts;
    private List<String> characteristics;
    private JobType jobType;
}
