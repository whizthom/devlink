package com.devlink1.devlink1.Controller;

import com.devlink1.devlink1.Entity.User;
import com.devlink1.devlink1.Service.SkillService.SkillService;
import com.devlink1.devlink1.Service.UserService.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/skill")
public class SkillController {


    private final UserService userService;

    public SkillController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/new")
    public String addNewSkillForm(@AuthenticationPrincipal UserDetails userDetails,
                                  Model model) {

        User user = userService.findByUsername(userDetails.getUsername());

        model.addAttribute("user", user);

        return "skill-addNew";

    }
}
