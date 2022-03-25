package com.greenfox.reddit.repositories;

import com.greenfox.reddit.models.UpVotePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpVotePostRepository extends JpaRepository<UpVotePost, Integer> {

  UpVotePost findByPostIdAndUserId(int postId, int userId);
}
