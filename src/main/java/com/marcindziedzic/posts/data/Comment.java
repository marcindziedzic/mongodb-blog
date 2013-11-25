package com.marcindziedzic.posts.data;

import org.bson.types.ObjectId;

public class Comment {

    private ObjectId id;
    private String content;

    public Comment(String content) {
        this.id = new ObjectId();
        this.content = content;
    }

    public String getId() {
        return id.toString();
    }

    public String getContent() {
        return content;
    }
}
