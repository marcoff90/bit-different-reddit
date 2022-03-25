package com.greenfox.reddit.services.postservices;

import com.greenfox.reddit.models.Post;
import java.util.List;

public interface PostService {

  boolean addPost(Post post);

  void upvotePost(int id);

  void downVotePost(int id);

  List<Post> findAll();

  Post getNewestPost();

  Post findById(int id);

  boolean wasPostAdded(Post post);

  List<Integer> getPages();

  List<Post> getSubList(int page);

}
