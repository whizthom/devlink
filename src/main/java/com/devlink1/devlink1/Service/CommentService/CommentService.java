package com.devlink1.devlink1.Service.CommentService;

import com.devlink1.devlink1.Entity.Comment;

import java.util.List;

public interface CommentService {
    Comment addComment(Comment comment);
    List<Comment> getComments();
    Comment getCommentById(long id);
    void deleteComment(long id);
}
