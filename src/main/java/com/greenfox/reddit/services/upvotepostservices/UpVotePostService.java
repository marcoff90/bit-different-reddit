package com.greenfox.reddit.services.upvotepostservices;

import com.greenfox.reddit.models.Post;
import com.greenfox.reddit.models.UpVotePost;
import com.greenfox.reddit.models.User;
import java.util.List;

public interface UpVotePostService {

  void addUpVotePost(Post post, User user);

  boolean wasPostUpVoted(int postId, int userId);

  UpVotePost findByPostIdAndUserId(int postId, int userId);

  void save(UpVotePost upVotePost);

}
