package com.marcindziedzic.posts.services;

import com.marcindziedzic.posts.data.Post;
import com.marcindziedzic.posts.data.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreatePostServiceTest {

    @Mock Post savedPost;
    @Mock PostRepository repository;

    @Captor ArgumentCaptor<Post> postCaptor;
    @InjectMocks CreatePostService service;

    @Before
    public void setUp() {
        when(repository.save(postCaptor.capture())).thenReturn(savedPost);
    }

    @Test
    public void shouldCreatePost() {
        service.run("Title", "Content");

        Post post = postCaptor.getValue();

        assertThat(post.getTitle(), equalTo("Title"));
        assertThat(post.getContent(), equalTo("Content"));

        verify(repository).save(post);
    }

    @Test
    public void shouldReturnSavedPost() {
        Post post = service.run("Title", "Content");
        assertThat(post, equalTo(savedPost));
    }

    @Test
    public void shouldNotCreatePostWithoutTitle() {
        Post post = service.run(null, "Content");
        assertThat(post, equalTo(null));
    }

    @Test
    public void shouldNotCreatePostWithEmptyTitle() {
        Post post = service.run("", "Content");
        assertThat(post, equalTo(null));
    }
}
