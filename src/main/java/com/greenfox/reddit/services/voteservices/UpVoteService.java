package com.greenfox.reddit.services.voteservices;

import com.greenfox.reddit.models.Comment;
import com.greenfox.reddit.models.Post;
import com.greenfox.reddit.models.UpVote;
import com.greenfox.reddit.models.User;

public interface UpVoteService {

  void save(UpVote upVote);

  boolean wasCommentUpVoted(int postId, int userId, int commentId);

  UpVote findByPostIdAndUserIdAndCommentId(int postId, int userId, int commentId);

  void addUpVoteForComment(Post post, User user, Comment comment);

}
