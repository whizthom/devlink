package com.devlink1.devlink1.Service.ProjectService;

import com.devlink1.devlink1.Entity.Project;
import com.devlink1.devlink1.Repository.ProjectRepository;
import com.devlink1.devlink1.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Project addnew(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project getProjectById(long id) {
        return projectRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteProjectById(long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
