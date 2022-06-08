package com.evo.apatios.repository;

import com.evo.apatios.model.Post;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PostRepository {

    private final Map<UUID, Post> posts = new HashMap<>(){};

    public void autoFill(){
        posts.put(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Backend Senior Developer"));
        posts.put(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"), new Post(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"), "Backend Middle Developer"));
    }

    public void saveAll(Map<UUID, Post> posts){
        this.posts.putAll(posts);
    }

    public Post save(Post post){
        posts.put(post.getId(), post);
        return post;
    }

    public Map<UUID, Post> findAll(){
        return new HashMap<>(posts);
    }

    public Optional<Post> findById(UUID id) {
        return Optional.ofNullable(posts.get(id));
    }

    public void deleteById(UUID id){
        posts.remove(posts.get(id));
    }

    public Optional<Post> findByName(String postName) {
        return posts.values().stream()
                .filter(post -> post.getName().toLowerCase().contains(postName.toLowerCase()))
                .findFirst();
    }
}
