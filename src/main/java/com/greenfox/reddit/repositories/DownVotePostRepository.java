package com.greenfox.reddit.repositories;

import com.greenfox.reddit.models.DownVotePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DownVotePostRepository extends JpaRepository<DownVotePost, Integer> {

  DownVotePost findByPostIdAndUserId(int postId, int userId);

}
