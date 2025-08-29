package com.devlink1.devlink1.Controller;

import com.devlink1.devlink1.Entity.Experience;
import com.devlink1.devlink1.Entity.User;
import com.devlink1.devlink1.Repository.ExperienceRepository;
import com.devlink1.devlink1.Service.ExperienceService.ExperienceService;
import com.devlink1.devlink1.Service.UserService.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/experience")
public class ExperienceController {

    private final ExperienceService experienceService;
    private final UserService userService;

    public ExperienceController(ExperienceService experienceService, UserService userService) {
        this.experienceService = experienceService;
        this.userService = userService;
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable long id) {
        experienceService.deleteExperienceById(id);
        System.out.println("Experience with id" + " " + id +" is deleted");

        return "redirect:/dashboard";
    }

    @GetMapping("/edit-form/{id}")
    public String editExperienceForm(@PathVariable("id") long id, Model model) {

        //get the expereince by Id
        Experience experience = experienceService.getExperienceById(id);

        model.addAttribute("exp", experience);

        return "experience-edit";
    }

    @PostMapping("/update")
    public String updateExperience(@ModelAttribute("exp") Experience experience,
                                   @AuthenticationPrincipal UserDetails userDetails) {

        // get the current user
        User user = userService.findByUsername(userDetails.getUsername());

        //Link the current user to the expereince object
        experience.setUser(user);
        System.out.println("Experience with id" + " " + experience.getId() + " is linked with user "
                + user.getUsername() + " with id " + user.getId());

//        save the experience
        experienceService.addExperience(experience);
        System.out.println("Experience with id" + " " + experience.getId() + " is updated");


        return "redirect:/dashboard";
    }


}
