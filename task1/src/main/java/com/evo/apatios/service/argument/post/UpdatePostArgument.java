package com.evo.apatios.service.argument.post;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UpdatePostArgument {

    private UUID id;

    private String name;
}
