package com.devlink1.devlink1.Controller;

import com.devlink1.devlink1.Entity.User;
import com.devlink1.devlink1.Service.UserService.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/image")
public class ImageController {

    private final UserService userService;

    public ImageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/upload")
    public String upload(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        // get current user
        User user = userService.findByUsername(userDetails.getUsername());

        model.addAttribute("user", user);

        return "image-upload";
    }


}
