package com.evo.apatios.service.post;

import com.evo.apatios.exception.NotFoundException;
import com.evo.apatios.model.Post;
import com.evo.apatios.repository.PostRepository;
import com.evo.apatios.service.argument.post.CreatePostArgument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PostServiceTest {

    private final PostRepository repository = Mockito.mock(PostRepository.class);

    private final PostService service = new PostService(repository);

    @Test
    void create() {
        //arrange
        Post mock = Mockito.mock(Post.class);
        CreatePostArgument argument = CreatePostArgument.builder()
                                                        .name("post name")
                                                        .build();

        when(repository.save(any())).thenReturn(mock);
        //act
        Post post = service.create(argument);
        //assert
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);

        verify(repository).save(postCaptor.capture());
        Post capturedPost = postCaptor.getValue();

        Assertions.assertEquals(post, mock);
        assertThat(capturedPost).usingRecursiveComparison()
                                .ignoringFields("id")
                                .isEqualTo(argument);
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
    void getNotExistingById() {
        //arrange
        when(repository.findById(any())).thenReturn(Optional.empty());
        //act + assert
        Assertions.assertThrows(NotFoundException.class,
                                () -> service.getExistingById(any()));

        verify(repository).findById(any());
    }
}