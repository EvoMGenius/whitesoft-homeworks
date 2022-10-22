package com.evo.bunkov.action.argument;

import com.evo.bunkov.model.Contacts;
import com.evo.bunkov.model.JobType;
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
