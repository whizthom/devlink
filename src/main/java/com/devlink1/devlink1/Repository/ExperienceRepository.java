package com.devlink1.devlink1.Repository;

import com.devlink1.devlink1.Entity.Experience;
import com.devlink1.devlink1.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
    List<Experience> findByUser(User user);
}
