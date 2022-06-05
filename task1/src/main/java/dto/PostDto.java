package dto;

import lombok.Data;

import java.util.UUID;

@Data
public class PostDto {

    private UUID id;

    private String name;
}
