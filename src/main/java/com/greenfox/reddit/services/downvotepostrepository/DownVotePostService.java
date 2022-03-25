package com.greenfox.reddit.services.downvotepostrepository;

import com.greenfox.reddit.models.DownVotePost;
import com.greenfox.reddit.models.Post;
import com.greenfox.reddit.models.User;
import java.util.List;

public interface DownVotePostService {

  void addDownVotePost(Post post, User user);

  boolean wasPostDownVoted(int postId, int userId);

  DownVotePost findByPostIdAndUserId(int postId, int userId);

  List<DownVotePost> findAll();

}
