package com.evo.bunkov.controller.post.mapper;

import com.evo.bunkov.dto.input.post.CreatePostDto;
import com.evo.bunkov.dto.input.post.UpdatePostDto;
import com.evo.bunkov.dto.output.post.PostDto;
import com.evo.bunkov.model.Post;
import com.evo.bunkov.service.argument.post.CreatePostArgument;
import com.evo.bunkov.service.argument.post.UpdatePostArgument;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {

    PostMapper POST_MAPPER = Mappers.getMapper(PostMapper.class);

    PostDto entityToDto(Post post);

    CreatePostArgument createDtoToArgument(CreatePostDto dto);

    UpdatePostArgument updateDtoToArgument(UpdatePostDto dto);
}
