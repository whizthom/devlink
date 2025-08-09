package com.devlink1.devlink1.Service.SkillService;

import com.devlink1.devlink1.Entity.Skill;

public interface SkillService {
    Skill findByName(String name);
    Skill save(Skill skill);
}
