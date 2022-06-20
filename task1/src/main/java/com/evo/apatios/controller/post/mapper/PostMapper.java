package com.evo.apatios.controller.post.mapper;

import com.evo.apatios.service.argument.CreationPostArgument;
import com.evo.apatios.dto.PostDto;
import com.evo.apatios.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public CreationPostArgument dtoToCreationArgument(PostDto postDto) {
        return new CreationPostArgument(postDto.getId(), postDto.getName());
    }

    public PostDto entityToDto(Post post) {
        return new PostDto(post.getId(), post.getName());
    }
}
