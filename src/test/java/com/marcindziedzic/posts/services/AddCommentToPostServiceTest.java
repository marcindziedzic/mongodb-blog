package com.marcindziedzic.posts.services;

import com.marcindziedzic.posts.data.Comment;
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
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AddCommentToPostServiceTest {

    @Mock Comment comment;
    @Mock Post post;
    @Mock PostRepository repository;

    @InjectMocks AddCommentToPostService service;

    @Before
    public void setUp() {
        when(repository.findOne("id")).thenReturn(post);
        when(repository.save(post)).thenReturn(post);

        when(post.addComment("content")).thenReturn(comment);
    }

    @Test
    public void shouldAddComment() {
        Comment newlyCreatedComment = service.run("id", "content");

        assertThat(newlyCreatedComment, equalTo(comment));
        verify(post).addComment("content");
        verify(repository).save(post);
    }

    @Test
    public void shouldNotAddCommentToNotExistingPost() {
        Comment comment = service.run("ID", "content");

        assertThat(comment, equalTo(null));
        verifyZeroInteractions(post);
        verify(repository).findOne("ID");
        verifyNoMoreInteractions(repository);
    }

}
