package com.devlink1.devlink1.Repository;

import com.devlink1.devlink1.Entity.Like;
import com.devlink1.devlink1.Entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Like,Long> {

    List<Like> findLikesByUser_Id(Long id);
    Like findLikesByUser_Username(String username);
    Like findLikeByProject(Project project);
}
