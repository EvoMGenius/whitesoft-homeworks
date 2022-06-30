package com.evo.apatios.service.post;

import com.evo.apatios.exception.NotFoundPostException;
import com.evo.apatios.model.Post;
import com.evo.apatios.repository.PostRepository;
import com.evo.apatios.service.argument.post.CreatePostArgument;
import com.evo.apatios.service.argument.post.UpdatePostArgument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;

    public List<Post> findAll(){
        return repository.findAll();
    }

    public Post create(CreatePostArgument createPostArgument){
        return repository.save(Post.builder()
                .name(createPostArgument.getName())
                .build());
    }

    public void deleteById(UUID id){
        repository.deleteById(id);
    }

    public Post getExistingById(UUID id){
        return repository.findById(id).orElseThrow(NotFoundPostException::new);
    }
    @PostConstruct
    public void init(){
        repository.saveAll(List.of(
                new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Backend Senior Developer"),
                new Post(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"), "Backend Middle Developer")));
    }

    public Post update(UpdatePostArgument updatePostArgument) {
        Post existedPost = repository.findById(updatePostArgument.getId()).orElseThrow(NotFoundPostException::new);
        existedPost.setName(updatePostArgument.getName());
        return repository.save(existedPost);
    }
}
