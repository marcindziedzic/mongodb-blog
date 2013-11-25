package com.marcindziedzic.posts.services;

import com.marcindziedzic.posts.data.Comment;
import com.marcindziedzic.posts.data.Post;
import com.marcindziedzic.posts.data.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddCommentToPostService {

    @Autowired
    private PostRepository repository;

    public Comment run(String id, String content) {
        Post post = repository.findOne(id);
        if (post == null) {
            return null;
        }

        return addComment(post, content);
    }

    private Comment addComment(Post post, String content) {
        Comment comment = post.addComment(content);
        repository.save(post);
        return comment;
    }
}
