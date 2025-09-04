package com.devlink1.devlink1.Service.LikeService;

import com.devlink1.devlink1.Entity.Like;
import com.devlink1.devlink1.Repository.LikesRepository;
import com.devlink1.devlink1.Repository.ProjectRepository;
import com.devlink1.devlink1.Repository.UserRepository;
import org.springframework.stereotype.Service;

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
}
