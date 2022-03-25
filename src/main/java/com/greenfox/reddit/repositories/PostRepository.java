package com.greenfox.reddit.repositories;

import com.greenfox.reddit.models.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

  @Query("SELECT p FROM Post p ORDER BY p.votes DESC, p.date DESC")
  List<Post> findAllAndOrderByVotes();

  Post findByUrl(String url);

}
