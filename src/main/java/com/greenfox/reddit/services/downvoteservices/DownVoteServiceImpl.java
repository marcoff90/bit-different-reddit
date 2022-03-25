package com.greenfox.reddit.services.downvoteservices;

import com.greenfox.reddit.models.Comment;
import com.greenfox.reddit.models.DownVote;
import com.greenfox.reddit.models.Post;
import com.greenfox.reddit.models.User;
import com.greenfox.reddit.repositories.DownVoteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DownVoteServiceImpl implements DownVoteService {

  private DownVoteRepository downVoteRepository;

  @Autowired
  public DownVoteServiceImpl(DownVoteRepository downVoteRepository) {
    this.downVoteRepository = downVoteRepository;
  }

  @Override
  public boolean wasCommentDownVoted(int postId, int userId, int commentId) {
    return findByPostIdAndUserIdAndCommentId(postId, userId, commentId).isWasDownVoted();
  }

  @Override
  public DownVote findByPostIdAndUserIdAndCommentId(int postId, int userId, int commentId) {
    return downVoteRepository.findByPostIdAndUserIdAndCommentId(postId, userId, commentId);
  }

  @Override
  public void addUpDownVoteForComment(Post post, User user, Comment comment) {
    downVoteRepository.save(new DownVote(post, user, comment));
  }
}
