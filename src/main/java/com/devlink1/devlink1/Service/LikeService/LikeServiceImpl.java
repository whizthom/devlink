package com.devlink1.devlink1.Service.LikeService;

import com.devlink1.devlink1.Entity.Like;
import com.devlink1.devlink1.Entity.Project;
import com.devlink1.devlink1.Entity.User;
import com.devlink1.devlink1.Repository.LikesRepository;
import com.devlink1.devlink1.Repository.ProjectRepository;
import com.devlink1.devlink1.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikesRepository likesRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public LikeServiceImpl(LikesRepository likesRepository, UserRepository userRepository, ProjectRepository projectRepository) {
        this.likesRepository = likesRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public void deleteLike(long id) {
        likesRepository.deleteById(id);
    }

    @Override
    public Like addLike(Like like) {
        return likesRepository.save(like);
    }

    @Override
    public Optional<Like> findByUserAndProject(User user, Project project) {
        return likesRepository.findByUserAndProject(user, project);
    }

    @Override
    public int countByProject(Project project) {
        return likesRepository.countByProject(project);
    }

    @Override
    public void removeLike(Like like) {
        likesRepository.delete(like);
    }

    @Override
    public boolean toggleLike(User user, Project project) {
        // check if like already exists
        Optional<Like> existing = likesRepository.findByUserAndProject(user, project);

        if (existing.isPresent()) {
            // user already liked -> unlike (remove it)
            likesRepository.delete(existing.get());
            return false; // now unliked
        } else {
            // user hasn’t liked -> create new like
            Like like = new Like();
            like.setUser(user);
            like.setProject(project);
            likesRepository.save(like);
            return true; // now liked
        }
    }
}
