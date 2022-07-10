package com.evo.apatios.service.post;

import com.evo.apatios.exception.NotFoundException;
import com.evo.apatios.model.Post;
import com.evo.apatios.repository.PostRepository;
import com.evo.apatios.service.argument.post.CreatePostArgument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PostServiceTest {

    private final PostRepository repository = Mockito.mock(PostRepository.class);

    private final PostService service = new PostService(repository);

    @Test
    void create() {
        //arrange
        UUID postId = UUID.randomUUID();
        Post mock = Mockito.mock(Post.class);
        CreatePostArgument argument = new CreatePostArgument( "post name");

        when(repository.save(any())).thenReturn(mock);
        //act
        Post post = service.create(argument);
        //assert
        Assertions.assertEquals(post , mock);

        verify(repository).save(any());
    }


    @Test
    void getExistingById() {
        //arrange
        Post post = mock(Post.class);

        when(repository.findById(any())).thenReturn(Optional.of(post));
        //act
        Post existingById = service.getExistingById(any());
        //assert
        Assertions.assertEquals(existingById, post);
    }

    @Test
    void getNotExistingById(){
        //arrange
        when(repository.findById(any())).thenReturn(Optional.empty());
        //act + assert
        Assertions.assertThrows(NotFoundException.class,
                () -> service.getExistingById(any()));

        verify(repository).findById(any());
    }
}