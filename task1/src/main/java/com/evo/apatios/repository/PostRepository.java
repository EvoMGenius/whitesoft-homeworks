package com.evo.apatios.repository;

import com.evo.apatios.model.Employee;
import com.evo.apatios.model.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PostRepository {

    private final List<Post> posts = new ArrayList<>();
    public List<Post> findAll() {
        return new ArrayList<>(posts);
    }

    public Post save(Post post) {
        Optional<Post> optionalOfExistedPost = posts.stream().filter(post1 ->
                        post1.getName().toLowerCase().equals(post.getName().toLowerCase()))
                .findFirst();
        if (optionalOfExistedPost.isPresent()) {
            Post existedPost = optionalOfExistedPost.get();
            int i = posts.indexOf(existedPost);
            post.setId(existedPost.getId());
            posts.add(i, post);
            return post;
        }
        post.setId(UUID.randomUUID());
        posts.add(post);
        return post;
    }

        public Optional<Post> findById (UUID id){
            return posts.stream().filter(post -> post.getId().equals(id)).findFirst();
        }

        public Optional<Post> findByName (String postName){
            return posts.stream().filter(post -> post.getName().equals(postName)).findFirst();
        }

        public void deleteById (UUID id){
            Optional<Post> first = posts.stream().filter(post -> post.getId().equals(id)).findFirst();
            first.ifPresent(posts::remove);
        }

        public void saveAll (List <Post> posts) {
            posts.forEach(this::save);
        }

}
