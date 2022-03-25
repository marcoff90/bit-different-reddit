package com.greenfox.reddit.services.commentservices;

import com.greenfox.reddit.models.Comment;
import java.util.List;

public interface CommentService {

  List<Comment> findAllComments(int id);
  List<Comment> findTopThree(int id);
  List<Comment> findAllAfterTopThree(int id);

  boolean addComment(String description, int id);

  void upVoteComment(int postId, int commentId);

  void downVoteComment(int postId, int commentId);

  Comment findById(int id);

}
