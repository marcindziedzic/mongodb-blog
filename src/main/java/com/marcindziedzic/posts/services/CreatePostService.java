package com.marcindziedzic.posts.services;

import com.marcindziedzic.posts.data.Post;
import com.marcindziedzic.posts.data.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.util.StringUtils.hasText;

@Service
public class CreatePostService {

    @Autowired
    private PostRepository repository;

    public Post run(String title, String content) {
        if (!hasText(title)) {
            return null;
        }

        return createPost(title, content);
    }

    private Post createPost(String title, String content) {
        Post post = new Post(title, content);
        return repository.save(post);
    }
}
