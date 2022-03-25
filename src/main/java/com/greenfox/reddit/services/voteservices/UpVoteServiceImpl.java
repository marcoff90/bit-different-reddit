package com.greenfox.reddit.services.voteservices;

import com.greenfox.reddit.models.Comment;
import com.greenfox.reddit.models.Post;
import com.greenfox.reddit.models.UpVote;
import com.greenfox.reddit.models.User;
import com.greenfox.reddit.repositories.UpVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpVoteServiceImpl implements UpVoteService {

  private UpVoteRepository upVoteRepository;

  @Autowired
  public UpVoteServiceImpl(UpVoteRepository upVoteRepository) {
    this.upVoteRepository = upVoteRepository;
  }

  @Override
  public void save(UpVote upVote) {
    upVoteRepository.save(upVote);
  }

  @Override
  public boolean wasCommentUpVoted(int postId, int userId, int commentId) {
    return findByPostIdAndUserIdAndCommentId(postId, userId, commentId).isWasUpvoted();
  }

  @Override
  public UpVote findByPostIdAndUserIdAndCommentId(int postId, int userId, int commentId) {
    return upVoteRepository.findByPostIdAndUserIdAndCommentId(postId, userId, commentId);
  }

  @Override
  public void addUpVoteForComment(Post post, User user, Comment comment) {
    upVoteRepository.save(new UpVote(post, user, comment));
  }
}
