package com.greenfox.reddit.repositories;

import com.greenfox.reddit.models.UpVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpVoteRepository extends JpaRepository<UpVote, Integer> {

  UpVote findByPostIdAndUserIdAndCommentId(int postId, int userId, int commentId);

}
