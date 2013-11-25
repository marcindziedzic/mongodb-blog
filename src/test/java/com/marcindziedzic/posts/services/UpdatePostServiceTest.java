package com.marcindziedzic.posts.services;

import com.marcindziedzic.posts.data.Post;
import com.marcindziedzic.posts.data.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdatePostServiceTest {

    @Mock Post post;
    @Mock PostRepository repository;

    @InjectMocks UpdatePostService service;

    @Before
    public void setUp() {
        when(repository.findOne("id")).thenReturn(post);
        when(repository.save(post)).thenReturn(post);
    }

    @Test
    public void shouldUpdatePost() {
        service.run("id", "content");

        verify(post).setContent("content");
        verify(repository).save(post);
    }

    @Test
    public void shouldReturnUpdatedPost() {
        Post updated = service.run("id", "content");
        assertThat(updated, equalTo(post));
    }

    @Test
    public void shouldNotUpdatePostIfNotExists() {
        Post updated = service.run("ID", "content");
        assertThat(updated, equalTo(null));
    }
}
