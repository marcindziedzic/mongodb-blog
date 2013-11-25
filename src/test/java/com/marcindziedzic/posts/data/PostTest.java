package com.marcindziedzic.posts.data;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class PostTest {

    Post post = new Post("title", "content");

    @Test
    public void shouldAddComment() {
        Comment comment = post.addComment("comment");

        assertThat(comment.getId(), notNullValue());
        assertThat(comment.getContent(), equalTo("comment"));
    }

    @Test
    public void shouldReturnAllCommentsOnDemand() {
        Comment comment1 = post.addComment("comment1");
        Comment comment2 = post.addComment("comment2");

        List<Comment> comments = post.getComments();

        assertThat(comments.size(), equalTo(2));
        assertTrue(comments.containsAll(asList(comment1, comment2)));
    }
}
