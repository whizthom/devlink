package com.devlink1.devlink1.Service.LikeService;

import com.devlink1.devlink1.Entity.Like;

public interface LikeService {
    void deleteLike(long id);
    Like addLike(Like like);
}
