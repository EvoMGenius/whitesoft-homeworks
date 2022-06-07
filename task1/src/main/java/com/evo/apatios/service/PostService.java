package com.evo.apatios.service;

import com.evo.apatios.argument.CreationPostArgument;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.evo.apatios.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.evo.apatios.repository.PostRepository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService {

    private final PostRepository repository;
    private final ObjectMapper mapper;

    @Autowired
    public PostService(PostRepository repository, ObjectMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Map<UUID, Post> findAll(){
        return repository.findAll();
    }

    public Post create(CreationPostArgument post){
        return repository.save(mapper.convertValue(post, Post.class));
    }

    public Optional<Post> findById(UUID id){
        return repository.findById(id);
    }

    public Optional<Post> findByName(String postName) {
        return repository.findByName(postName);
    }

    public void autoFill() {
        repository.autoFill();
    }
}
