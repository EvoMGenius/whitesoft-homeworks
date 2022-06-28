package com.evo.apatios.service.post;

import com.evo.apatios.exception.NotFoundPostException;
import com.evo.apatios.model.Post;
import com.evo.apatios.repository.PostRepository;
import com.evo.apatios.service.argument.CreationPostArgument;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PostServiceTest {

    private final PostRepository repository = Mockito.mock(PostRepository.class);

    private final PostService service = new PostService(repository);

    @Test
    void create() {
        //arrange

        CreationPostArgument argument = new CreationPostArgument(UUID.randomUUID(), "post name");
        when(repository.save(any())).thenReturn(new Post(argument.getId(), argument.getName()));

        //act

        Post post = service.create(argument);

        //assert

        Assertions.assertEquals(post , new Post(argument.getId(), argument.getName()));

        verify(repository).save(any());
    }


    @Test
    void getExistingById() {
        //arrange

        UUID postId = UUID.randomUUID();

        when(repository.findById(postId)).thenReturn(Optional.of(new Post(postId, "post name")));

        //act

        Post existingById = service.getExistingById(postId);

        //assert

        Assertions.assertNotNull(existingById);

        verify(repository).findById(postId);
    }

    @Test
    void getNotExistingById(){
        //arrange

        UUID postId = UUID.randomUUID();

        when(repository.findById(postId)).thenReturn(Optional.empty());

        //act + assert

        Assertions.assertThrows(NotFoundPostException.class,
                () -> service.getExistingById(postId));

        verify(repository).findById(postId);
    }
}