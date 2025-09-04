package com.devlink1.devlink1.Service.CommentService;

import com.devlink1.devlink1.Entity.Comment;
import com.devlink1.devlink1.Repository.CommentRepository;
import com.devlink1.devlink1.Repository.ProjectRepository;
import com.devlink1.devlink1.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }


    @Override
    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }

    @Override
    public Comment getCommentById(long id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }
}
