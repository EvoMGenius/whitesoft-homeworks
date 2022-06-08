package com.evo.apatios.service;

import com.evo.apatios.model.Contacts;
import com.evo.apatios.model.JobType;
import com.evo.apatios.model.Post;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CreationEmployeeArgument {
    private UUID id;

    private String firstName;
    private String lastName;
    private String description;
    private Post post;

    private Contacts contacts;
    private List<String> characteristics;
    private JobType jobType;
}
