package com.evo.apatios.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class CreationPostArgument {
    private UUID id;

    private String name;
}
