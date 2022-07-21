package com.evo.apatios.service.params;

import lombok.*;

import java.util.UUID;

@Data
@Builder
public class SearchParams {
    private String firstName;
    private String lastName;
    private UUID postId;
}
