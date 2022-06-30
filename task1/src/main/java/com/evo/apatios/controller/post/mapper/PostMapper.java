package com.evo.apatios.controller.post.mapper;

import com.evo.apatios.dto.input.post.CreatePostDto;
import com.evo.apatios.dto.input.post.UpdatePostDto;
import com.evo.apatios.dto.output.post.PostDto;
import com.evo.apatios.model.Post;
import com.evo.apatios.service.argument.post.CreatePostArgument;
import com.evo.apatios.service.argument.post.UpdatePostArgument;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface PostMapper {

    PostDto entityToDto(Post post);

    CreatePostArgument createDtoToArgument(CreatePostDto dto);

    UpdatePostArgument updateDtoToArgument(UpdatePostDto dto);
}
