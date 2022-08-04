package com.evo.bunkov.service.post;

import com.evo.bunkov.exception.NotFoundException;
import com.evo.bunkov.model.Post;
import com.evo.bunkov.repository.PostRepository;
import com.evo.bunkov.service.argument.post.CreatePostArgument;
import com.evo.bunkov.service.argument.post.UpdatePostArgument;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;

    public List<Post> findAll() {
        return repository.findAll();
    }

    public Post create(CreatePostArgument createPostArgument) {
        return repository.save(Post.builder()
                                   .name(createPostArgument.getName())
                                   .build());
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    public Post getExistingById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("post with this id is not found", id));
    }

    @PostConstruct
    public void init() {
        repository.saveAll(List.of(
                new Post(UUID.fromString("854ef89d-6c27-4635-926d-894d76a81707"), "Backend Senior Developer"),
                new Post(UUID.fromString("762d15a5-3bc9-43ef-ae96-02a680a557d0"), "Backend Middle Developer")));
    }

    @Transactional
    public Post update(UUID id, UpdatePostArgument updatePostArgument) {
        Post existedPost = getExistingById(id);
        existedPost.setName(updatePostArgument.getName());
        return repository.save(existedPost);
    }
}
