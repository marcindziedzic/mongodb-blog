package com.marcindziedzic.posts.data;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.LinkedList;
import java.util.List;

public class Post {

    @Id
    private ObjectId id;
    private String title;
    private String content;
    private List<Comment> comments = new LinkedList<Comment>();

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Comment addComment(String content) {
        Comment comment = new Comment(content);
        comments.add(comment);

        return comment;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id.toString();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getCreationTime() {
        return id.getTime();
    }

    public List<Comment> getComments() {
        return comments;
    }
}
