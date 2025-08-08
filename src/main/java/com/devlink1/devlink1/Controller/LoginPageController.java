package com.devlink1.devlink1.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginPageController {

    @GetMapping("/")
    public String HomePage() {
        return "home";
    }


    @GetMapping("/login")
    public String LoginPageController() {
        return "loginpage";
    }


}
