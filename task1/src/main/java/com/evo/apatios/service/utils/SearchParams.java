package com.evo.apatios.service.utils;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class SearchParams {
    private String firstName;
    private String lastName;
    private UUID postId;
}
