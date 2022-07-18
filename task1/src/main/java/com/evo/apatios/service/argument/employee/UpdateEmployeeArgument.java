package com.evo.apatios.service.argument.employee;

import com.evo.apatios.model.Contacts;
import com.evo.apatios.model.JobType;
import com.evo.apatios.model.Post;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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
