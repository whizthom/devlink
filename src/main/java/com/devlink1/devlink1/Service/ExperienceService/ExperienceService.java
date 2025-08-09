package com.devlink1.devlink1.Service.ExperienceService;

import com.devlink1.devlink1.Entity.Experience;

import java.util.List;

public interface ExperienceService {
    List<Experience> getAllExperience();
    Experience getExperienceById(Long id);
    Experience addExperience(Experience experience);
}
