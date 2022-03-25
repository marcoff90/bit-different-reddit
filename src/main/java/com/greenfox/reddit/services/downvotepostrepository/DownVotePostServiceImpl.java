package com.greenfox.reddit.services.downvotepostrepository;

import com.greenfox.reddit.models.DownVotePost;
import com.greenfox.reddit.models.Post;
import com.greenfox.reddit.models.User;
import com.greenfox.reddit.repositories.DownVotePostRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DownVotePostServiceImpl implements DownVotePostService {

  private DownVotePostRepository downVotePostRepository;

  public DownVotePostServiceImpl(DownVotePostRepository downVoteRepository) {
    this.downVotePostRepository = downVoteRepository;
  }

  @Override
  public void addDownVotePost(Post post, User user) {
    this.downVotePostRepository.save(new DownVotePost(post, user));
  }

  @Override
  public boolean wasPostDownVoted(int postId, int userId) {
    return findByPostIdAndUserId(postId, userId).isWasDownVoted();
  }

  @Override
  public DownVotePost findByPostIdAndUserId(int postId, int userId) {
    return downVotePostRepository.findByPostIdAndUserId(postId, userId);
  }

  @Override
  public List<DownVotePost> findAll() {
    return null;
  }
}
