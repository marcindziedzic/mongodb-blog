package com.marcindziedzic.posts.rest;

import com.marcindziedzic.posts.data.Comment;
import com.marcindziedzic.posts.data.Post;
import com.marcindziedzic.posts.data.PostRepository;
import com.marcindziedzic.posts.services.AddCommentToPostService;
import com.marcindziedzic.posts.services.CreatePostService;
import com.marcindziedzic.posts.services.DeletePostService;
import com.marcindziedzic.posts.services.UpdatePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostRepository repository;

    @Autowired
    private CreatePostService createPostService;
    @Autowired
    private UpdatePostService updatePostService;
    @Autowired
    private DeletePostService deletePostService;
    @Autowired
    private AddCommentToPostService addCommentToPostService;

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public Post create(@RequestParam(value = "title", required = true) String title,
                       @RequestParam(value = "content", required = true) String content) {
        return createPostService.run(title, content);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Post read(@PathVariable String id) {
        return repository.findOne(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable String id,
                       @RequestParam(value = "content", required = true) String content) {
        updatePostService.run(id, content);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable String id) {
        deletePostService.run(id);
    }

    @RequestMapping(value = "/{id}/comment", method = RequestMethod.PUT)
    @ResponseBody
    public Comment addComment(@PathVariable String id,
                              @RequestParam(value = "content", required = true) String content) {
        return addCommentToPostService.run(id, content);
    }

}
