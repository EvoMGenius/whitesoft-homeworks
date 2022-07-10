package com.evo.apatios.controller.post;

import com.evo.apatios.dto.input.post.CreatePostDto;
import com.evo.apatios.dto.input.post.UpdatePostDto;
import com.evo.apatios.dto.output.post.PostDto;
import com.evo.apatios.model.Post;
import com.evo.apatios.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.evo.apatios.controller.post.mapper.PostMapper.POST_MAPPER;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/list")
    public List<PostDto> findAll(){
        return postService.findAll().stream().map(POST_MAPPER::entityToDto).toList();
    }

    @GetMapping("/{id}")
    public PostDto findById(@PathVariable UUID id){
        Post post = postService.getExistingById(id);
        return POST_MAPPER.entityToDto(post);
    }

    @PostMapping
    public PostDto create(@RequestBody CreatePostDto postDto){
        Post createdPost = postService.create(POST_MAPPER.createDtoToArgument(postDto));
        return POST_MAPPER.entityToDto(createdPost);
    }

    @PutMapping("/{id}")
    public PostDto update(@PathVariable UUID id,
                          @RequestBody UpdatePostDto postDto){

        Post updatedPost = postService.update(id,POST_MAPPER.updateDtoToArgument(postDto));
        return POST_MAPPER.entityToDto(updatedPost);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id){
        postService.deleteById(id);
    }
}
