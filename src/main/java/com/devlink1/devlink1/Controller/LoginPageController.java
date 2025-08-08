package com.devlink1.devlink1.Controller;

import com.devlink1.devlink1.Entity.User;
import com.devlink1.devlink1.Service.UserService.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginPageController {

    private final UserService userService;

    public LoginPageController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String HomePage() {
        return "home";
    }


    @GetMapping("/login")
    public String LoginPageController(@Valid User user, Model model) {
        model.addAttribute("user", user);
        return "loginpage";
    }

    @GetMapping("/profile-setup")
    public String loginUser(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        User user = userService.findByUsername(userDetails.getUsername());

        model.addAttribute("user", user);

        return "profile-setup";
    }

//    @PostMapping("/profile/setup/update")
//    public String updateUser(@RequestParam("user") User user, Model model) {
//
//    }


}
