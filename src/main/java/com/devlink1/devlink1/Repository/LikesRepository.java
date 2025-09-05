package com.devlink1.devlink1.Repository;

import com.devlink1.devlink1.Entity.Like;
import com.devlink1.devlink1.Entity.Project;
import com.devlink1.devlink1.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Like,Long> {

    List<Like> findLikesByUser_Id(Long id);
    Like findLikesByUser_Username(String username);
    Like findLikeByProject(Project project);

    // find a like by user and project
    Optional<Like> findByUserAndProject(User user, Project project);

    // Count likes for a project
    int countByProject(Project project);
}
