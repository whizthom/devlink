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
import java.util.List;
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

//        set to go straight to dashboard if the user progress is 100
        if(user.getProgress() == 100){
            return "redirect:/dashboard";
        }
        
        Experience experience = new Experience();
        Certification certification = new Certification();

        // set the User progress to 10% if the user is new to set up
        int progress;
        if(user.getProgress() == 0){
            user.setProgress(10);
            progress = user.getProgress();
        }else{
            progress = user.getProgress();
        }


        model.addAttribute("user", user);
        model.addAttribute("experience", experience);
        model.addAttribute("certification", certification);
//        model.addAttribute("progress", 15);
        model.addAttribute("progress", progress);

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

        // set the User progress to 10%
        int progress;
        existingUser.setProgress(35);

        userService.addNewUser(existingUser);



        // I increased the progress bar
//        model.addAttribute("progress", 35);


        System.out.println("User Profile Updated");

        return "forward:/experience-setup";
    }


    // EXperience Controller

    @PostMapping("/experience-setup")
    public String experienceSetup(@AuthenticationPrincipal UserDetails userDetails,
                            Model model) {

        User user = userService.findByUsername(userDetails.getUsername());
        Experience experience = new Experience();
//        Certification certification = new Certification();

//        Get the stored progress
        int progress = user.getProgress();


        model.addAttribute("user", user);
        model.addAttribute("experience", experience);
//        model.addAttribute("certification", certification);
//        model.addAttribute("progress", 35);
        model.addAttribute("progress", progress);

        return "profile-setup";
    }

    @PostMapping("/profile/setup/experience")
    public String saveExperience(@ModelAttribute("experience") Experience experience,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        experience.setUser(user);  // link experience to user

//        Set the user progress
        user.setProgress(75);
        experienceService.addExperience(experience);
        userService.addNewUser(user);


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

        // get and set progress
        int progress = user.getProgress();


        model.addAttribute("user", user);
        model.addAttribute("experience", experience);
        model.addAttribute("certification", certification);
//        model.addAttribute("progress", 75);
        model.addAttribute("progress", progress);

        return "profile-setup";
    }

    @PostMapping("/profile/setup/certification")
    public String saveCertification(@ModelAttribute("certification") Certification
                                                certification,
                                 @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());

        user.setProgress(100);
        certification.setUser(user);  // link experience to user
        certificationService.addCertification(certification);

        System.out.println("Certification Updated");

        return "profile-setup";
    }

    // Dashboard

    @GetMapping("/dashboard")
    public String dashboard(@AuthenticationPrincipal UserDetails userDetails,
                                     Experience exp, Certification cert,
                                     Model model) {

        User user = userService.findByUsername(userDetails.getUsername());


        //get the list of the user experience
        List<Experience> experiences = experienceService.findByUser(user);

        //get the list of the user certifications
        List<Certification> certifications = user.getCertifications();

        model.addAttribute("exp",exp);
        model.addAttribute("cert",cert);
        model.addAttribute("user", user);
        model.addAttribute("experiences", experiences);
        model.addAttribute("certifications", certifications);

        return "dashboard";
    }

    // Forward the user to the Skill and Bio Setup from Expereience
    @GetMapping("/profile/setup/back/step1")
    public String goBacktoSkillandBioPage(@AuthenticationPrincipal UserDetails userDetails){

        //Get the current User
        User user = userService.findByUsername(userDetails.getUsername());

        //Get the current User progress and set it to 10
        user.setProgress(10);
        userService.addNewUser(user);

        return "redirect:/profile-setup";
    }

    // Forward the user to the Skill and Bio Setup from Expereience
    @GetMapping("/profile/setup/back/step2")
    public String goBacktoExperiencePage(@AuthenticationPrincipal UserDetails userDetails){

        //Get the current User
        User user = userService.findByUsername(userDetails.getUsername());

        //Get the current User progress and set it to 10
        user.setProgress(35);
        userService.addNewUser(user);

        return "redirect:/profile-setup";
    }

}
