package com.evo.apatios.controller.post;

import com.evo.apatios.controller.post.mapper.PostMapper;
import com.evo.apatios.dto.input.post.CreatePostDto;
import com.evo.apatios.dto.input.post.UpdatePostDto;
import com.evo.apatios.dto.output.post.PostDto;
import com.evo.apatios.model.Post;
import com.evo.apatios.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    private final PostMapper postMapper;

    @Autowired
    public PostController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @GetMapping("/all")
    public List<PostDto> getAll(){
        return postService.findAll().stream().map(postMapper::entityToDto).toList();
    }

    @PostMapping
    public PostDto create(@RequestBody CreatePostDto postDto){
        Post createdPost = postService.create(postMapper.createDtoToArgument(postDto));
        return postMapper.entityToDto(createdPost);
    }

    @PutMapping
    public PostDto update(@RequestBody UpdatePostDto postDto){
        Post updatedPost = postService.update(postMapper.updateDtoToArgument(postDto));
        return postMapper.entityToDto(updatedPost);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id){
        postService.deleteById(id);
    }

}
