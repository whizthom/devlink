package com.devlink1.devlink1.Controller;

import com.devlink1.devlink1.Entity.Certification;
import com.devlink1.devlink1.Entity.Experience;
import com.devlink1.devlink1.Entity.Skill;
import com.devlink1.devlink1.Entity.User;
import com.devlink1.devlink1.Service.CertificationService.CertificationService;
import com.devlink1.devlink1.Service.ExperienceService.ExperienceService;
import com.devlink1.devlink1.Service.SkillService.SkillService;
import com.devlink1.devlink1.Service.UserService.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class LoginPageController {

    private final UserService userService;
    private final SkillService skillService;
    private final ExperienceService experienceService;
    private final CertificationService certificationService;

    public LoginPageController(UserService userService, SkillService skillService, ExperienceService experienceService, CertificationService certificationService) {
        this.userService = userService;
        this.skillService = skillService;
        this.experienceService = experienceService;
        this.certificationService = certificationService;
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
    public String loginUser(@AuthenticationPrincipal UserDetails userDetails,
                            Model model) {

        User user = userService.findByUsername(userDetails.getUsername());
        Experience experience = new Experience();
        Certification certification = new Certification();


        model.addAttribute("user", user);
        model.addAttribute("experience", experience);
        model.addAttribute("certification", certification);
        model.addAttribute("progress", 15);

        return "profile-setup";
    }


    @PostMapping("/profile/setup/update")
    public String updateUser(@AuthenticationPrincipal UserDetails userDetails,
                             @ModelAttribute("user") User formUser,
                             @RequestParam("skillsInput") String skillsInput,
                             Model model) {


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

        //set the  field attributes
        existingUser.setSkills(skillEntities);
        existingUser.setBio(formUser.getBio());

        userService.addNewUser(existingUser);

        // I increased the progress bar
        model.addAttribute("progress", 35);
        model.addAttribute("", existingUser);


        System.out.println("User Profile Updated");

        return "forward:/experience-setup";
    }


    // EXperience Controller

    @PostMapping("/experience-setup")
    public String experienceSetup(@AuthenticationPrincipal UserDetails userDetails,
                            Model model) {

        User user = userService.findByUsername(userDetails.getUsername());
        Experience experience = new Experience();
        Certification certification = new Certification();


        model.addAttribute("user", user);
        model.addAttribute("experience", experience);
        model.addAttribute("certification", certification);
        model.addAttribute("progress", 35);

        return "profile-setup";
    }

    @PostMapping("/profile/setup/experience")
    public String saveExperience(@ModelAttribute("experience") Experience experience,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        experience.setUser(user);  // link experience to user
        experienceService.addExperience(experience);

        System.out.println("Experience Updated");

        return "forward:/certification-setup";
    }


    // Certification Controller
    @PostMapping("/certification-setup")
    public String certificationSetup(@AuthenticationPrincipal UserDetails userDetails,
                                  Model model) {

        User user = userService.findByUsername(userDetails.getUsername());
        Experience experience = new Experience();
        Certification certification = new Certification();


        model.addAttribute("user", user);
        model.addAttribute("experience", experience);
        model.addAttribute("certification", certification);
        model.addAttribute("progress", 75);

        return "profile-setup";
    }

    @PostMapping("/profile/setup/certification")
    public String saveCertification(@ModelAttribute("certification") Certification certification,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        certification.setUser(user);  // link experience to user
        certificationService.addCertification(certification);

        System.out.println("Certification Updated");

        return "forward:/dashboard";
    }

    // Dashboard

    @PostMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails,
                                     Model model) {

        User user = userService.findByUsername(userDetails.getUsername());
        Experience experience = new Experience();
        Certification certification = new Certification();


        model.addAttribute("user", user);
        model.addAttribute("experience", experience);
        model.addAttribute("certification", certification);
        model.addAttribute("progress", 100);

        return "dashboard";
    }







}
