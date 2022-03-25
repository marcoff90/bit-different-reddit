package com.greenfox.reddit.repositories;

import com.greenfox.reddit.models.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

  @Query("SELECT c FROM Comment c WHERE c.post.id = ?1 ORDER BY c.votes DESC, c.date DESC ")
  List<Comment> findAllByPostIdAndOrderByVotes(int id);

}
