package com.evo.bunkov.controller.post;

import com.evo.bunkov.dto.input.post.CreatePostDto;
import com.evo.bunkov.dto.input.post.UpdatePostDto;
import com.evo.bunkov.dto.output.post.PostDto;
import com.evo.bunkov.model.Post;
import com.evo.bunkov.service.post.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.evo.bunkov.controller.post.mapper.PostMapper.POST_MAPPER;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@Api(tags = "post-controller")
public class PostController {

    private final PostService postService;

    @GetMapping("/list")
    @ApiOperation("Вывод списка должностей.")
    public List<PostDto> findAll() {
        return postService.findAll().stream().map(POST_MAPPER::entityToDto).toList();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Найти должность по id.", response = PostDto.class)
    public PostDto findById(@PathVariable UUID id) {
        Post post = postService.getExistingById(id);
        return POST_MAPPER.entityToDto(post);
    }

    @PostMapping
    @ApiOperation(value = "Создать должность.", response = PostDto.class)
    public PostDto create(@RequestBody CreatePostDto postDto) {
        Post createdPost = postService.create(POST_MAPPER.createDtoToArgument(postDto));
        return POST_MAPPER.entityToDto(createdPost);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Изменить должность.", response = PostDto.class)
    public PostDto update(@PathVariable UUID id,
                          @RequestBody UpdatePostDto postDto) {
        Post updatedPost = postService.update(id, POST_MAPPER.updateDtoToArgument(postDto));
        return POST_MAPPER.entityToDto(updatedPost);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Удалить должность по id.")
    public void deleteById(@PathVariable UUID id) {
        postService.deleteById(id);
    }
}
