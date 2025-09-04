package com.devlink1.devlink1.Controller;

import com.devlink1.devlink1.Entity.User;
import com.devlink1.devlink1.Service.CertificationService.CertificationService;
import com.devlink1.devlink1.Service.CommentService.CommentService;
import com.devlink1.devlink1.Service.ExperienceService.ExperienceService;
import com.devlink1.devlink1.Service.LikeService.LikeService;
import com.devlink1.devlink1.Service.ProjectService.ProjectService;
import com.devlink1.devlink1.Service.SkillService.SkillService;
import com.devlink1.devlink1.Service.UserService.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/link")
public class LinkupController {

    private final UserService userService;
    private final ProjectService projectService;
    private final CommentService commentService;
    private final CertificationService certificationService;
    private final ExperienceService experienceService;
    private final LikeService likeService;
    private final SkillService skillService;

    public LinkupController(UserService userService, ProjectService projectService, CommentService commentService, CertificationService certificationService, ExperienceService experienceService, LikeService likeService, SkillService skillService) {
        this.userService = userService;
        this.projectService = projectService;
        this.commentService = commentService;
        this.certificationService = certificationService;
        this.experienceService = experienceService;
        this.likeService = likeService;
        this.skillService = skillService;
    }

    // controller to get all the user project timeline
    @GetMapping("/view")
    public String view(@AuthenticationPrincipal UserDetails userDetails,
                       Model model) {

        //get current user
        User currentUser = userService.findByUsername(userDetails.getUsername());

        // get all users
        List<User> users = userService.getAllUsers();

        model.addAttribute("users", users);
        model.addAttribute("currentUser", currentUser);

        return"link-up";
    }
}
