package com.greenfox.reddit.services.upvotepostservices;

import com.greenfox.reddit.models.Post;
import com.greenfox.reddit.models.UpVotePost;
import com.greenfox.reddit.models.User;
import com.greenfox.reddit.repositories.UpVotePostRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpVotePostServiceImpl implements UpVotePostService {

  private UpVotePostRepository upVotePostRepository;

  @Autowired
  public UpVotePostServiceImpl(UpVotePostRepository upVotePostRepository) {
    this.upVotePostRepository = upVotePostRepository;
  }

  @Override
  public void addUpVotePost(Post post, User user) {
    upVotePostRepository.save(new UpVotePost(post, user));
  }

  @Override
  public boolean wasPostUpVoted(int postId, int userId) {
    return findByPostIdAndUserId(postId, userId).isWasUpvoted();
  }

  @Override
  public UpVotePost findByPostIdAndUserId(int postId, int userId) {
    return upVotePostRepository.findByPostIdAndUserId(postId, userId);
  }

  @Override
  public void save(UpVotePost upVotePost) {
    upVotePostRepository.save(upVotePost);
  }

}
