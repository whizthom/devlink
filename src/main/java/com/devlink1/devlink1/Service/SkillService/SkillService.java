package com.devlink1.devlink1.Service.SkillService;

import com.devlink1.devlink1.Entity.Skill;
import com.devlink1.devlink1.Entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

public interface SkillService {
    Skill findByName(String name);
    Skill save(Skill skill);
    Skill findById(long id);
    void removeSkill(User user, long id);
}
