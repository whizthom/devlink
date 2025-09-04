package com.devlink1.devlink1.Controller;

import com.devlink1.devlink1.Entity.Skill;
import com.devlink1.devlink1.Entity.User;
import com.devlink1.devlink1.Service.SkillService.SkillService;
import com.devlink1.devlink1.Service.UserService.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/skill")
public class SkillController {


    private final UserService userService;
    private final SkillService skillService;

    public SkillController(UserService userService, SkillService skillService) {
        this.userService = userService;
        this.skillService = skillService;
    }

    @GetMapping("/new")
    public String addNewSkillForm(@AuthenticationPrincipal UserDetails userDetails,
                                  Model model) {

        User user = userService.findByUsername(userDetails.getUsername());

        model.addAttribute("user", user);

        return "skill-addNew";

    }

    @PostMapping("/add")
    public String updateUser(@AuthenticationPrincipal UserDetails userDetails,
                             @ModelAttribute("user") User formUser,
                             @RequestParam("skillsInput") String skillsInput) {

        User existingUser = userService.findByUsername(userDetails.getUsername());


        if(formUser.getPassword() !=null && !formUser.getPassword().isEmpty()) {
            existingUser.setPassword(formUser.getPassword());
        }


        Set<Skill> skillEntities = Arrays.stream(skillsInput.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(name -> {
                    Skill skill = skillService.findByName(name);
                    if (skill != null) return skill;
                    Skill newSkill = new Skill();
                    newSkill.setName(name);
                    return skillService.save(newSkill);
                })
                .collect(Collectors.toSet());


        // Get the current user skill(s)
        Set<Skill> existingSkills = existingUser.getSkills();

        //Merge the skills with the pre exisitng ones attributes
        existingSkills.addAll(skillEntities);


        //set the skills
        existingUser.setSkills(existingSkills);

//        existingUser.setBio(formUser.getBio());

        userService.addNewUser(existingUser);

        System.out.println("Username "+existingUser.getUsername()+" has added new skill(s)");

        return "redirect:/dashboard";
    }

    @GetMapping("/remove/{id}")
    public String removeSkill(@AuthenticationPrincipal UserDetails userDetails
            ,@PathVariable int id) {


//         Get the current user
        User user = userService.findByUsername(userDetails.getUsername());

        // Get the current skill to be removed
        Skill skill = skillService.findById(id);

        // remove the skill from the list
        skillService.removeSkill(user,id);


        // print out
        System.out.println("username "+user.getUsername()+" has removed skill " +
                "with id " +id + " and name >>> "+skill.getName());



        return "redirect:/dashboard";
    }

}
