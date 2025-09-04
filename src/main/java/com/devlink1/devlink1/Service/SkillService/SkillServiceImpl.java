package com.devlink1.devlink1.Service.SkillService;

import com.devlink1.devlink1.Entity.Skill;
import com.devlink1.devlink1.Entity.User;
import com.devlink1.devlink1.Repository.SkillRepository;
import com.devlink1.devlink1.Repository.UserRepository;
import com.devlink1.devlink1.Service.UserService.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SkillServiceImpl implements SkillService {

    private SkillRepository skillRepository;
    private UserRepository userRepository;

    public SkillServiceImpl(SkillRepository skillRepository, UserRepository userRepository) {
        this.skillRepository = skillRepository;
        this.userRepository = userRepository;
    }



    @Override
    public Skill findByName(String name) {
        return skillRepository.findByNameIgnoreCase(name).orElse(null);
    }

    @Override
    public Skill save(Skill skill) {
        return skillRepository.save(skill);
    }

    @Override
    public Skill findById(long id) {
        return skillRepository.findById(id).orElse(null);
    }

    @Override
    public void removeSkill(User user, long id) {


        // Get the current skill to be removed
        Skill skill = skillRepository.findById(id).
                orElseThrow(()->new RuntimeException("skill not found"));

        //remove the skill
        user.getSkills().remove(skill);


        //persist the update
        userRepository.save(user);

    }
}
