package com.devlink1.devlink1.Controller;

import com.devlink1.devlink1.Entity.Comment;
import com.devlink1.devlink1.Entity.Project;
import com.devlink1.devlink1.Entity.User;
import com.devlink1.devlink1.Service.CommentService.CommentService;
import com.devlink1.devlink1.Service.ProjectService.ProjectService;
import com.devlink1.devlink1.Service.UserService.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final ProjectService projectService;

    public CommentController(CommentService commentService, UserService userService, ProjectService projectService) {
        this.commentService = commentService;
        this.userService = userService;
        this.projectService = projectService;
    }

    // Add a new Comment
    @PostMapping("/new/{id}")
    public String newComment(@AuthenticationPrincipal UserDetails userDetails
            , @PathVariable int id, String content) {

        //Find the current User
        User user = userService.findByUsername(userDetails.getUsername());

        // find the current project
        Project project = projectService.getProjectById(id);

        //create new comment object
        Comment comment = new Comment();

        //link the comment to the user and project
        comment.setAuthor(user);
        comment.setProject(project);
        comment.setContent(content);

        //print
        System.out.println("New comment is linked with the username "+
                user.getUsername() + " with id " + user.getId() +
        " and project with id "+project.getId());

        //persist the comment
        commentService.addComment(comment);

        //print
        System.out.println("comment added");

        return "redirect:/project/view";
    }
}
