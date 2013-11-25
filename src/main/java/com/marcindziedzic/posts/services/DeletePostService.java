package com.marcindziedzic.posts.services;

import com.marcindziedzic.posts.data.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.util.StringUtils.hasText;

@Service
public class DeletePostService {

    @Autowired
    private PostRepository repository;

    public void run(String id) {
        if (hasText(id)) {
            repository.delete(id);
        }
    }
}
