package com.evo.apatios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PostDto {

    private UUID id;

    private String name;

}
