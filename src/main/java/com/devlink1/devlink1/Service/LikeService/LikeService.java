package com.devlink1.devlink1.Service.LikeService;

import com.devlink1.devlink1.Entity.Like;
import com.devlink1.devlink1.Entity.Project;
import com.devlink1.devlink1.Entity.User;

import java.util.Optional;

public interface LikeService {
    void deleteLike(long id);
    Like addLike(Like like);
    Optional<Like> findByUserAndProject(User user, Project project);
    int countByProject(Project project);
    void removeLike(Like like);
    boolean toggleLike(User user, Project project);
}
