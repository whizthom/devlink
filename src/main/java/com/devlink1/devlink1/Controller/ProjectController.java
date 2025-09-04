package com.devlink1.devlink1.Controller;

import com.devlink1.devlink1.Entity.Comment;
import com.devlink1.devlink1.Entity.Project;
import com.devlink1.devlink1.Entity.User;
import com.devlink1.devlink1.Service.ProjectService.ProjectService;
import com.devlink1.devlink1.Service.UserService.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private final UserService userService;
    private final ProjectService projectService;

    public ProjectController(UserService userService, ProjectService projectService) {
        this.userService = userService;
        this.projectService = projectService;
    }


    @GetMapping("/view")
    public String getProject(@AuthenticationPrincipal UserDetails userDetails, Project project,
                             Comment comment,
                             Model model) {

        User user = userService.findByUsername(userDetails.getUsername());

        model.addAttribute("user", user);
        model.addAttribute("project", project);
        model.addAttribute("comment", comment);
        return "project2";
    }

    //Controller to display the form to add a new project
    @GetMapping("/add")
    public String addProject(Project project, Model model) {


        model.addAttribute("project", project);

        return "project-addNew";
    }

    //Controller to add a new project to the DB (also used this to update a project)
    @PostMapping("/new")
    public String addProject(@AuthenticationPrincipal UserDetails userDetails,
                             @ModelAttribute("project") Project project) {
        // Get the current user
        User user = userService.findByUsername(userDetails.getUsername());
        System.out.println("The username "+ user.getUsername() + " is about to add a new project");

        // link the project object to the current user
        project.setUser(user);
        System.out.println("New project is linked with the username "+ user.getUsername() + " with id " + user.getId());

        projectService.addnew(project);
        System.out.println("New project with id "+project.getId()+" is added");

        return "redirect:/project/view";
    }

    //controller to get the form to edit an existing project
    @GetMapping("/edit-form/{id}")
    public String editForm(@PathVariable int id, Model model) {

        // get the current project
        Project project = projectService.getProjectById(id);

        //send the model attribute to the form
        model.addAttribute("project", project);

        return "project-edit";
    }

    //Method to delete the form from the DB
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {

        // delete the project
        projectService.deleteProjectById(id);

        //print
        System.out.println("Successfully deleted project with id "+id);

        return "redirect:/project/view";
    }


}
