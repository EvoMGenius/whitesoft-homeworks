package com.evo.apatios.argument;

import lombok.Data;

import java.util.UUID;

@Data
public class CreationPostArgument {
    private UUID id;

    private String name;
}
