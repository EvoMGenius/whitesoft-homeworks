package com.evo.apatios.dto;

import com.evo.apatios.dto.transfer.New;
import com.evo.apatios.dto.transfer.Update;
import com.evo.apatios.model.Contacts;
import com.evo.apatios.model.JobType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class EmployeeDto {
    @Null(groups = {New.class})
    @NotNull(groups = {Update.class})
    private UUID id;

    private String firstName;
    private String lastName;
    private String description;
    private UUID postId;

    private Contacts contacts;
    private List<String> characteristics;
    private JobType jobType;
}
