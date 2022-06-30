package com.evo.apatios.service.argument.employee;

import com.evo.apatios.model.Contacts;
import com.evo.apatios.model.JobType;
import com.evo.apatios.model.Post;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateEmployeeAgrument {

    private String firstName;
    private String lastName;
    private String description;
    private Post post;

    private Contacts contacts;
    private List<String> characteristics;
    private JobType jobType;
}
