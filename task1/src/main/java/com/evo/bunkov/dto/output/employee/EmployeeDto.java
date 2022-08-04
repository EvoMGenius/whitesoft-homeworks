package com.evo.bunkov.dto.output.employee;

import com.evo.bunkov.dto.output.post.PostDto;
import com.evo.bunkov.model.Contacts;
import com.evo.bunkov.model.JobType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@ApiModel("Модель сотрудника.")
public class EmployeeDto {

    private UUID id;

    @ApiModelProperty("Имя.")
    private String firstName;
    @ApiModelProperty("Фамилия.")
    private String lastName;
    @ApiModelProperty("Описание сотрудника.")
    private String description;
    @ApiModelProperty("Должность сотрудника.")
    private PostDto post;

    @ApiModelProperty("Контактные данные сотрудника.")
    private Contacts contacts;
    @ApiModelProperty("Характеристики сотрудника.")
    private List<String> characteristics;
    @ApiModelProperty("Тип работы.")
    private JobType jobType;
}
