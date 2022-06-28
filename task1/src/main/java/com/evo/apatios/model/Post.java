package com.evo.apatios.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
@Entity
@Data
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    private String name;
}