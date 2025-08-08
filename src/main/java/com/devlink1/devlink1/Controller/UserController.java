package com.devlink1.devlink1.Controller;

import com.devlink1.devlink1.Entity.Role;
import com.devlink1.devlink1.Entity.User;
import com.devlink1.devlink1.Service.UserService.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/signupform")
    public String signUpForm(Model model) {

        User newUser = new User();
        model.addAttribute("user", newUser);
        return "signupform";
    }

    @PostMapping("/signup")
    public String signUp(User user, Model model) {

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
            user.setRole(Role.User);
            userService.addNewUser(user);
            return "profile-setup";
        }
    }
}
