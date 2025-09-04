package com.devlink1.devlink1.Service.ProjectService;

import com.devlink1.devlink1.Entity.Project;

import java.util.List;

public interface ProjectService {

    Project addnew(Project project);
    Project getProjectById(long id);
    void deleteProjectById(long id);
    List<Project> getAllProjects();
}
