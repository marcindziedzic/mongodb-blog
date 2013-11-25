package com.marcindziedzic.posts.rest;

import com.marcindziedzic.posts.data.Comment;
import com.marcindziedzic.posts.data.Post;
import com.marcindziedzic.posts.data.PostRepository;
import com.marcindziedzic.posts.services.AddCommentToPostService;
import com.marcindziedzic.posts.services.CreatePostService;
import com.marcindziedzic.posts.services.DeletePostService;
import com.marcindziedzic.posts.services.UpdatePostService;
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
public class PostControllerTest {

    @Mock Post post;
    @Mock Comment comment;

    @Mock PostRepository repository;

    @Mock CreatePostService createPostService;
    @Mock UpdatePostService updatePostService;
    @Mock DeletePostService deletePostService;
    @Mock AddCommentToPostService addCommentToPostService;

    @InjectMocks PostController controller;

    @Test
    public void shouldDelegateCreatePostRequestToUnderlyingService() {
        when(createPostService.run("title", "content")).thenReturn(post);
        Post post = controller.create("title", "content");
        assertThat(post, equalTo(this.post));
    }

    @Test
    public void shouldDelegateUpdatePostRequestToUnderlyingService() {
        controller.update("id", "content");
        verify(updatePostService).run("id", "content");
    }

    @Test
    public void shouldDelegateDeletePostRequestToUnderlyingService() {
        controller.delete("id");
        verify(deletePostService).run("id");
    }

    @Test
    public void shouldDelegateReadPostRequestToUnderlyingService() {
        when(repository.findOne("id")).thenReturn(post);
        Post post = controller.read("id");
        assertThat(post, equalTo(this.post));
    }

    @Test
    public void shouldDelegateAddCommentToPostRequestToUnderlyingService() {
        when(addCommentToPostService.run("id", "content")).thenReturn(comment);
        Comment comment = controller.addComment("id", "content");
        assertThat(comment, equalTo(this.comment));
    }
}
