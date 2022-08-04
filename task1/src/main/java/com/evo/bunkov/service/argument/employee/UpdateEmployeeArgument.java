package com.evo.bunkov.service.argument.employee;

import com.evo.bunkov.model.Contacts;
import com.evo.bunkov.model.JobType;
import com.evo.bunkov.model.Post;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UpdateEmployeeArgument {

    private UUID id;

    private String firstName;
    private String lastName;
    private String description;
    private Post post;

    private Contacts contacts;
    private List<String> characteristics;
    private JobType jobType;
}
