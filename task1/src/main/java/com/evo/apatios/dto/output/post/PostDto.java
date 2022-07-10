package com.evo.apatios.dto.output.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@ApiModel("Модель должности.")
public class PostDto {

    private UUID id;

    @ApiModelProperty("Название должности.")
    private String name;
}
