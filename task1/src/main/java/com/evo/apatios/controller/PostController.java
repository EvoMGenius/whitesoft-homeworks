package com.evo.apatios.controller;

import com.evo.apatios.dto.PostDto;
import com.evo.apatios.mapper.PostMapper;
import com.evo.apatios.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public void autofill(){ postService.autoFill();}

    @GetMapping("/all")
    public List<PostDto> getAll(){
        return postService.findAll().values().stream().map(postMapper::entityToDto).toList();
    }

    @PostMapping
    public PostDto create(@RequestBody PostDto postDto){
        return postMapper.entityToDto(postService.create(postMapper.dtoToCreationArgument(postDto)));
    }

}
