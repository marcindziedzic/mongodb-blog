package com.marcindziedzic.posts.data;

import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, String> {
}
