package com.evo.apatios.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Post {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post)) return false;
        Post post = (Post) o;
        return id != null &&
               id.equals(post.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}