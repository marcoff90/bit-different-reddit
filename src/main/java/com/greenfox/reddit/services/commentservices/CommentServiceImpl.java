package com.greenfox.reddit.services.commentservices;

import com.greenfox.reddit.models.Comment;
import com.greenfox.reddit.repositories.CommentRepository;
import com.greenfox.reddit.services.downvoteservices.DownVoteService;
import com.greenfox.reddit.services.postservices.PostService;
import com.greenfox.reddit.services.userservices.UserService;
import com.greenfox.reddit.services.voteservices.UpVoteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

  private CommentRepository commentRepository;
  private PostService postService;
  private UserService userService;
  private UpVoteService upVoteService;
  private DownVoteService downVoteService;

  @Autowired
  public CommentServiceImpl(CommentRepository commentRepository, PostService postService, UserService userService,
      UpVoteService upVoteService, DownVoteService downVoteService) {
    this.commentRepository = commentRepository;
    this.postService = postService;
    this.userService = userService;
    this.upVoteService = upVoteService;
    this.downVoteService = downVoteService;
  }

  @Override
  public List<Comment> findAllComments(int id) {
    return commentRepository.findAllByPostIdAndOrderByVotes(id);
  }

  @Override
  public List<Comment> findTopThree(int id) {
    return findAllComments(id).size() > 3 ? findAllComments(id).subList(0, 3) : findAllComments(id);
  }

  @Override
  public List<Comment> findAllAfterTopThree(int id) {
    return findAllComments(id).size() > 3 ? findAllComments(id).subList(3, findAllComments(id).size()) : null;
  }

  @Override
  public boolean addComment(String description, int id) {
    Comment comment = new Comment(description);
    comment.setPost(postService.findById(id));
    if (userService.getCurrentUser() != null) {
      comment.setUser(userService.getCurrentUser());
    }
    commentRepository.save(comment);
    return true;
  }

  @Override
  public void upVoteComment(int postId, int commentId) {
    if (userService.getCurrentUser() != null && !upVoteService.wasCommentUpVoted(postId, userService.getCurrentUser().getId(), commentId)) {
      Comment comment = findById(commentId);
      comment.setVotes(comment.getVotes() + 1);
      upVoteService.findByPostIdAndUserIdAndCommentId(postId, userService.getCurrentUser().getId(), commentId).setWasUpvoted(true);
      upVoteService.save(upVoteService.findByPostIdAndUserIdAndCommentId(postId, userService.getCurrentUser().getId(), commentId));
      commentRepository.save(comment);
      // * checks if the vote hasnt been used for the comment by the user
    }
  }

  @Override
  public void downVoteComment(int postId, int commentId) {
    if (userService.getCurrentUser() != null && !downVoteService.wasCommentDownVoted(postId, userService.getCurrentUser().getId(),
        commentId)) {
      Comment comment = findById(commentId);
      comment.setVotes(comment.getVotes() - 1);
      downVoteService.findByPostIdAndUserIdAndCommentId(postId, userService.getCurrentUser().getId(), commentId).setWasDownVoted(true);
      commentRepository.save(comment);
    }
  }

  @Override
  public Comment findById(int id) {
    return commentRepository.findById(id).orElse(null);
  }

}
