package com.devlink1.devlink1.Controller;

import com.devlink1.devlink1.Entity.Certification;
import com.devlink1.devlink1.Entity.Experience;
import com.devlink1.devlink1.Entity.User;
import com.devlink1.devlink1.Repository.CertificationRepository;
import com.devlink1.devlink1.Service.CertificationService.CertificationService;
import com.devlink1.devlink1.Service.UserService.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequestMapping("/certificate")
public class CertificationController {

    private final CertificationService certificationService;
    private final UserService userService;

    public CertificationController(CertificationService certificationService, UserService userService) {
        this.certificationService = certificationService;
        this.userService = userService;
    }

    @GetMapping("/delete/{id}")
    public String deleteCertification(@PathVariable("id") long id, Sort sort) {

        certificationService.deleteById(id);
        System.out.println("certification with id" + " " + id +" is deleted");
        return "redirect:/dashboard";
    }

    @GetMapping("/edit-form/{id}")
    public String editCertification(@PathVariable("id") long id,
                                    Model model) {

        Certification certificationById = certificationService.findCertificationById(id);

        model.addAttribute("cert", certificationById);

        return "certificate-edit";
    }


    @PostMapping("/update")
    public String updateCertification(@ModelAttribute("cert") Certification certification,
                                      @AuthenticationPrincipal UserDetails userDetails) {

        long id = certification.getId();

        //Get the present user
        User user = userService.findByUsername(userDetails.getUsername());

        //link certificate with user
        certification.setUser(user);
        System.out.println("certification with id" + " " + id + " is linked with user " +
                user.getUsername() + " with id " + user.getId());

        //save the certification
        certificationService.addCertification(certification);
        System.out.println("Certification with id "+" " + id +" is updated");

        return "redirect:/dashboard";

    }


    @GetMapping("/addNew")
    public String addNewCertificateForm(Certification cert, Model model  ){

        model.addAttribute("cert", cert);

        return "certificate-addNew";
    }

    @PostMapping("/add")
    public String addNewExperience(@AuthenticationPrincipal UserDetails userDetails ,
                                   @ModelAttribute("cert") Certification cert){

        // Get the current user
        User user = userService.findByUsername(userDetails.getUsername());
        System.out.println("The username "+ user.getUsername() + " is about to add a new certification");

        // link the certification object to the current user
        cert.setUser(user);
        System.out.println("New certification is linked with the username "+ user.getUsername() + " with id " + user.getId());

        certificationService.addCertification(cert);
        System.out.println("New certification with id "+cert.getId()+" is added");

        return "redirect:/dashboard";
    }

}
