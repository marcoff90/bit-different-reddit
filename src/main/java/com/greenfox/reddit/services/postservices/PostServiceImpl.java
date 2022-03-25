package com.greenfox.reddit.services.postservices;

import com.greenfox.reddit.models.Post;
import com.greenfox.reddit.repositories.PostRepository;
import com.greenfox.reddit.services.downvotepostrepository.DownVotePostService;
import com.greenfox.reddit.services.upvotepostservices.UpVotePostService;
import com.greenfox.reddit.services.userservices.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {

  private PostRepository postRepository;
  private UserService userService;
  private DownVotePostService downVotePostService;
  private UpVotePostService upVotePostService;

  @Autowired
  public PostServiceImpl(PostRepository postRepository, UserService userService,
      DownVotePostService downVotePostService, UpVotePostService upVoteService) {
    this.postRepository = postRepository;
    this.userService = userService;
    this.downVotePostService = downVotePostService;
    this.upVotePostService = upVoteService;
  }

  @Override
  public boolean addPost(Post post) {
    boolean wasPostAdded = false;
    if (postRepository.findByUrl(post.getUrl()) == null) {
      if (userService.getCurrentUser() != null) {
        post.setUser(userService.getCurrentUser());
      }
      postRepository.save(post);
      wasPostAdded = true;
    } else {
      wasPostAdded = false;
    }
    // * checks dtb for match, if no match checks if user is logged in if so, sets user as the owner of the new post, if not saves as anonymous
    return wasPostAdded;
  }

  @Override
  public void upvotePost(int id) {
    if (userService.getCurrentUser() != null && !upVotePostService.wasPostUpVoted(id, userService.getCurrentUser().getId())) {
      Post post = findById(id);
      post.setVotes(post.getVotes() + 1);
      upVotePostService.findByPostIdAndUserId(id, userService.getCurrentUser().getId()).setWasUpvoted(true);
      postRepository.save(post);
    }
    // * if user logged in and post with post_id and user_id wasn't upvoted, upvotes, changes upvoted to true, saves post and through cascading also upvote
    // * same logic for downvote
  }

  @Override
  public void downVotePost(int id) {
    if (userService.getCurrentUser() != null && !downVotePostService.wasPostDownVoted(id, userService.getCurrentUser().getId())) {
      Post post = findById(id);
      post.setVotes(post.getVotes() - 1);
      downVotePostService.findByPostIdAndUserId(id, userService.getCurrentUser().getId()).setWasDownVoted(true);
      postRepository.save(post);
    }
  }

  @Override
  public List<Post> findAll() {
    List<Post> all = postRepository.findAllAndOrderByVotes();
    all.add(0, getNewestPost());
    return all.stream().distinct().collect(Collectors.toList()); // the newest post would be twice in the list
  }

  @Override
  public Post getNewestPost() {
    addPost(new Post("https://stackoverflow.com", "This is our first post!"));
    // * in order for this method to work when creating a new db, one object has to be instantiated
    return postRepository.findAll().get(postRepository.findAll().size() - 1);
  }

  @Override
  public Post findById(int id) {
    return postRepository.findById(id).orElse(null);
  }

  @Override
  public boolean wasPostAdded(Post post) {
    return postRepository.findByUrl(post.getUrl()) != null;
  }

  @Override
  public List<Integer> getPages() {

    int pagesCount = findAll().size() % 10 == 0 ? findAll().size() / 10 : findAll().size() / 10 + 1;
    List<Integer> pages = new ArrayList<>();
    for (int i = 1; i <= pagesCount; i++) {
      pages.add(i);
    }
    return pages;
  }

  @Override
  public List<Post> getSubList(int page) {
    int end = page * 10;
    int start = end - 10;
    try {
      return findAll().subList(start, end);
    } catch (Exception e) {
      return findAll().subList(start, findAll().size());
    }
  }

}
