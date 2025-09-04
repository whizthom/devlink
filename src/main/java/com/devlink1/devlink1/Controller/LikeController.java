package com.devlink1.devlink1.Controller;

import com.devlink1.devlink1.Entity.Like;
import com.devlink1.devlink1.Entity.Project;
import com.devlink1.devlink1.Entity.User;
import com.devlink1.devlink1.Service.LikeService.LikeService;
import com.devlink1.devlink1.Service.ProjectService.ProjectService;
import com.devlink1.devlink1.Service.UserService.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/like")
public class LikeController {

    private final LikeService likeService;
    private final UserService userService;
    private final ProjectService projectService;

    public LikeController(LikeService likeService,
                          UserService userService,
                          ProjectService projectService) {

        this.likeService = likeService;
        this.userService = userService;
        this.projectService = projectService;
    }

    // controller tp persist a new Like Object
    @PostMapping("/new/{id}")
    public String addNewLike(@PathVariable long id
            ,@AuthenticationPrincipal UserDetails userDetails) {

        // find the current user
        User user = userService.findByUsername(userDetails.getUsername());

        //Get the current project
        Project project = projectService.getProjectById(id);

        // Create and link the like object to the project and User
        Like like = new Like();

        like.setProject(project);
        like.setUser(user);

        //print
        System.out.println("like with id "+ like.getId() + " is linked with user "
                + user.getUsername() + " with id " + user.getId() +
                " and project with id " + project.getId());

        // Persist the like
        likeService.addLike(like);

        //print
        System.out.println("like with id "+ like.getId() + " is made on project with id "
                +project.getId());

        return "redirect:/project/view";
    }





}
