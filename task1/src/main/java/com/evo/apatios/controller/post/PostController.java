package com.evo.apatios.controller.post;

import com.evo.apatios.controller.post.mapper.PostMapper;
import com.evo.apatios.dto.PostDto;
import com.evo.apatios.dto.transfer.New;
import com.evo.apatios.dto.transfer.Update;
import com.evo.apatios.service.post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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
    public PostDto create(@RequestBody
                              @Validated(New.class) PostDto postDto){
        return postMapper.entityToDto(postService.create(postMapper.dtoToCreationArgument(postDto)));
    }

    @PutMapping
    public PostDto update(@RequestBody
                              @Validated({Update.class}) PostDto postDto){
        return postMapper.entityToDto(postService.create(postMapper.dtoToCreationArgument(postDto)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable UUID id){
        postService.deleteById(id);
    }

}
