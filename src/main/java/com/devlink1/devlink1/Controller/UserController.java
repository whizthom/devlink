package com.devlink1.devlink1.Controller;

import com.devlink1.devlink1.Entity.Role;
import com.devlink1.devlink1.Entity.Skill;
import com.devlink1.devlink1.Entity.User;
import com.devlink1.devlink1.Service.SkillService.SkillService;
import com.devlink1.devlink1.Service.UserService.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final SkillService skillService;

    public UserController(UserService userService, SkillService skillService) {
        this.userService = userService;
        this.skillService = skillService;
    }


    @GetMapping("/signupform")
    public String signUpForm(Model model) {

        User newUser = new User();
        model.addAttribute("user", newUser);
        return "signupform";
    }

    @PostMapping("/signup")
    public String signUp(User user, @ModelAttribute("user") User formUser,
                         Model model) {

        User username = userService.findByUsername(user.getUsername());
        User email = userService.findByEmail(user.getEmail());
        if(username != null) {
            model.addAttribute("error", "Username is already taken. Try another one.");
            List<User> getUsers = userService.getAllUsers();

            model.addAttribute("user", user);
//            model.addAttribute("users", getUsers);
            return "signupform";
        } else if (email != null) {
            model.addAttribute("error", "Email is already in use. Try another one.");
            return "signupform";
        } else{
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));



            // set the User progress to 10% if the user is new to set up
            int progress;
            if(user.getProgress() == 0){
                user.setProgress(10);
                progress = user.getProgress();
            }else{
                progress = user.getProgress();
            }

            model.addAttribute("progress", progress);

            user.setRole(Role.User);
            userService.addNewUser(user);
            return "profile-setup";
        }
    }
}
