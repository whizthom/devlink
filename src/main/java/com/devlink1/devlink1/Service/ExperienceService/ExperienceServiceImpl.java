package com.devlink1.devlink1.Service.ExperienceService;

import com.devlink1.devlink1.Entity.Experience;
import com.devlink1.devlink1.Repository.ExperienceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceRepository experienceRepository;

    public ExperienceServiceImpl(ExperienceRepository experienceRepository) {
        this.experienceRepository = experienceRepository;
    }


    @Override
    public List<Experience> getAllExperience() {
        return experienceRepository.findAll();
    }

    @Override
    public Experience getExperienceById(Long id) {
        return experienceRepository.findById(id).orElse(null);
    }

    @Override
    public Experience addExperience(Experience experience) {
        return experienceRepository.save(experience);
    }
}
