package com.marcindziedzic.posts.integrationtests;

import com.marcindziedzic.posts.data.Post;
import com.marcindziedzic.posts.data.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration("src/main/webapp/WEB-INF/web.xml")
@ContextConfiguration(locations = {"file:src/main/webapp/servlet-context.xml"})
public class PostControllerIntegrationTest {

    @Resource
    WebApplicationContext webApplicationContext;

    @Autowired
    PostRepository repository;

    MockMvc mvc;
    Post existingPost;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        existingPost = repository.save(new Post("Exemplary Post Title", "Exemplary Post Content"));
    }

    @Test
    public void shouldCreatePost() throws Exception {
        RequestBuilder request = put("/api/post")
                .param("title", "Title")
                .param("content", "Content");

        perform(request)
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Title"))
                .andExpect(jsonPath("$.content").value("Content"))
                .andExpect(jsonPath("$.creationTime").exists())
                .andExpect(jsonPath("$.comments").isArray());
    }

    @Test
    public void shouldDeletePost() throws Exception {
        perform(delete("/api/post/" + existingPost.getId()));
        assertThat(repository.findOne(existingPost.getId()), equalTo(null));
    }

    @Test
    public void shouldReadPost() throws Exception {
        perform(get("/api/post/" + existingPost.getId()))
                .andExpect(jsonPath("$.id").value(existingPost.getId()))
                .andExpect(jsonPath("$.title").value(existingPost.getTitle()))
                .andExpect(jsonPath("$.content").value(existingPost.getContent()))
                .andExpect(jsonPath("$.creationTime").value(existingPost.getCreationTime()))
                .andExpect(jsonPath("$.comments").value(existingPost.getComments()));
    }

    @Test
    public void shouldUpdatePost() throws Exception {
        RequestBuilder request = post("/api/post/" + existingPost.getId())
                .param("content", "Brand new content");

        perform(request);

        existingPost.setContent("Brand new content");
        assertThatActualPostMatches(existingPost);
    }

    @Test
    public void shouldAddCommentToPost() throws Exception {
        RequestBuilder postRequest = put("/api/post/" + existingPost.getId() + "/comment")
                .param("content", "Comment");

        perform(postRequest)
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.content").value("Comment"))
                .andExpect(jsonPath("$.title").doesNotExist());
    }

    private ResultActions perform(RequestBuilder request) throws Exception {
        return mvc.perform(request).andExpect(status().isOk());
    }

    private void assertThatActualPostMatches(Post expected) {
        Post actual = repository.findOne(expected.getId());
        assertThat(actual.getTitle(), equalTo(expected.getTitle()));
        assertThat(actual.getContent(), equalTo(expected.getContent()));
    }
}
