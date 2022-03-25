package com.greenfox.reddit.services.downvoteservices;

import com.greenfox.reddit.models.Comment;
import com.greenfox.reddit.models.DownVote;
import com.greenfox.reddit.models.Post;
import com.greenfox.reddit.models.User;
import java.util.List;

public interface DownVoteService {

  boolean wasCommentDownVoted(int postId, int userId, int commentId);

  DownVote findByPostIdAndUserIdAndCommentId(int postId, int userId, int commentId);

  void addUpDownVoteForComment(Post post, User user, Comment comment);
}
