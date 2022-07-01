package com.evo.apatios.controller.post;

import com.evo.apatios.controller.post.mapper.PostMapper;
import com.evo.apatios.dto.input.post.CreatePostDto;
import com.evo.apatios.dto.input.post.UpdatePostDto;
import com.evo.apatios.dto.output.post.PostDto;
import com.evo.apatios.model.Post;
import com.evo.apatios.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.evo.apatios.controller.post.mapper.PostMapper.POST_MAPPER;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/all")
    public List<PostDto> getAll(){
        return postService.findAll().stream().map(POST_MAPPER::entityToDto).toList();
    }

    @PostMapping
    public PostDto create(@RequestBody CreatePostDto postDto){
        Post createdPost = postService.create(POST_MAPPER.createDtoToArgument(postDto));
        return POST_MAPPER.entityToDto(createdPost);
    }

    @PutMapping
    public PostDto update(@RequestBody UpdatePostDto postDto){
        Post updatedPost = postService.update(POST_MAPPER.updateDtoToArgument(postDto));
        return POST_MAPPER.entityToDto(updatedPost);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id){
        postService.deleteById(id);
    }

}
