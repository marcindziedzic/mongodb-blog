package com.marcindziedzic.posts.services;

import com.marcindziedzic.posts.data.Post;
import com.marcindziedzic.posts.data.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdatePostService {

    @Autowired
    private PostRepository repository;

    public Post run(String id, String content) {
        Post post = repository.findOne(id);
        if (post == null) {
            return null;
        }

        return update(post, content);
    }

    private Post update(Post post, String content) {
        post.setContent(content);
        return repository.save(post);
    }
}
