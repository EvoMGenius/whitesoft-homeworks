package com.evo.apatios.dto.output.post;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ApiModel("Модель должности.")
public class PostDto {

    private UUID id;

    @ApiModelProperty("Название должности.")
    private String name;
}
