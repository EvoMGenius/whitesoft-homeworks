package services;

import models.Post;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PostsService {

    private Map<UUID, Post> posts = new HashMap<>();

    public void autofill(){
        posts.put(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Backend Senior Developer"));
        posts.put(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"), new Post(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"), "Backend Middle Developer"));
    }

    public void fill(Map<UUID, Post> posts){
        this.posts.putAll(posts);
    }

    public Post findPostByUUID(UUID uuid) {
        return posts.get(uuid);
    }
}
