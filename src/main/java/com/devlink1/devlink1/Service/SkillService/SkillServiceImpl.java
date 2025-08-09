package com.devlink1.devlink1.Service.SkillService;

import com.devlink1.devlink1.Entity.Skill;
import com.devlink1.devlink1.Repository.SkillRepository;
import com.devlink1.devlink1.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class SkillServiceImpl implements SkillService {

    private final UserRepository userRepository;
    private SkillRepository skillRepository;

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
}
