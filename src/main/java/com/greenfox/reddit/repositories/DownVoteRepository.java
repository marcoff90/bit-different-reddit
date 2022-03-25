package com.greenfox.reddit.repositories;

import com.greenfox.reddit.models.DownVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DownVoteRepository extends JpaRepository<DownVote, Integer> {

  DownVote findByPostIdAndUserIdAndCommentId(int postId, int userId, int commentId);

}
