package com.greenfox.reddit.controllers;

import com.greenfox.reddit.models.Post;
import com.greenfox.reddit.services.downvotepostrepository.DownVotePostService;
import com.greenfox.reddit.services.postservices.PostService;
import com.greenfox.reddit.services.upvotepostservices.UpVotePostService;
import com.greenfox.reddit.services.userservices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PostController {

  private PostService postService;
  private UserService userService;
  private UpVotePostService upVotePostService;
  private DownVotePostService downVotePostService;

  @Autowired
  public PostController(PostService postService, UserService userService, UpVotePostService upVotePostService,
      DownVotePostService downVotePostService) {
    this.postService = postService;
    this.userService = userService;
    this.upVotePostService = upVotePostService;
    this.downVotePostService = downVotePostService;
  }

  @GetMapping({"/{page}/posts", "/"})
  public String test(@PathVariable(required = false) Integer page, Model model) {
    page = page == null ? 1 : page;
    model.addAttribute("pages", postService.getPages());
    model.addAttribute("posts", postService.getSubList(page));
    model.addAttribute("user", userService.getCurrentUser());
    // * user model is rendered in html based on condition if null, nothing shows, if not renderes current user name
    doesModelContainsAttributes(model); // * not necessary, only thymeleaf is underlined red if not used
    return "reddit/index";
  }

  @PostMapping("/add")
  public String storePost(@ModelAttribute Post post, Model model, RedirectAttributes attributes) {
    model.addAttribute("user", userService.getCurrentUser());
    if (postService.addPost(post)) {
      attributes.addFlashAttribute("wasPostAdded", postService.wasPostAdded(post)); // * success alert
      return "redirect:/";
    } else {
      attributes.addFlashAttribute("didPostExist", true); // * already exists alert
      return "redirect:/";
    }
  }

  @GetMapping("/{id}/upvote")
  public String showUpvote(@PathVariable int id, Model model, RedirectAttributes attributes) {
    model.addAttribute("user", userService.getCurrentUser());
    if (userService.getCurrentUser() != null && upVotePostService.findByPostIdAndUserId(id, userService.getCurrentUser().getId()) == null) {
      upVotePostService.addUpVotePost(postService.findById(id), userService.getCurrentUser());
      // * if user is logged in and no match for post id and user id -> create UpVote object
      postService.upvotePost(id);
    } else {
      wasUserLoggedInAndDidAlreadyUpvoted(id, attributes);
    }
    return "redirect:/";
  }

  @GetMapping("/{id}/downVote")
  public String showDownVote(@PathVariable int id, Model model, RedirectAttributes attributes) {
    model.addAttribute("user", userService.getCurrentUser());
    if (userService.getCurrentUser() != null
        && downVotePostService.findByPostIdAndUserId(id, userService.getCurrentUser().getId()) == null) {
      downVotePostService.addDownVotePost(postService.findById(id), userService.getCurrentUser());
      // * if user is logged in and no match for post id and user id -> create UpVote object
      postService.downVotePost(id);
    } else {
      wasUserLoggedInAndDidAlreadyDownVoted(id, attributes);
    }
    return "redirect:/";
  }

  private Model doesModelContainsAttributes(Model model) {
    if (model.containsAttribute("notLoggedInUserVoting")) {
      model.addAttribute("notLoggedInUserVoting", model.getAttribute("notLoggedInUserVoting"));
    } else if (model.containsAttribute("didUserAlreadyDownvoted")) {
      model.addAttribute("didUserAlreadyDownvoted", model.getAttribute("didUserAlreadyDownvoted"));
    } else if (model.containsAttribute("didUserAlreadyUpVoted")) {
      model.addAttribute("didUserAlreadyUpVoted", model.getAttribute("didUserAlreadyUpVoted"));
    } else if (model.containsAttribute("wasPostAdded")) {
      model.addAttribute("wasPostAdded", model.getAttribute("wasPostAdded"));
    } else if (model.containsAttribute("wasUserFound")) {
      model.addAttribute("wasUserFound", model.getAttribute("wasUserFound"));
    } else if (model.containsAttribute("loggedOutUser")) {
      model.addAttribute("loggedOutUser", model.getAttribute("loggedOutUser"));
    } else if (model.containsAttribute("userCreated")) {
      model.addAttribute("userCreated", model.getAttribute("userCreated"));
    } else if (model.containsAttribute("didPostExist")) {
      model.addAttribute("didPostExist", model.getAttribute("didPostExist"));
    }
    return model;
  }

  private RedirectAttributes wasUserLoggedInAndDidAlreadyUpvoted(int id, RedirectAttributes attributes) {
    if (userService.getCurrentUser() == null) {
      attributes.addFlashAttribute("notLoggedInUserVoting", true);
    } else if (upVotePostService.wasPostUpVoted(id, userService.getCurrentUser().getId())) {
      attributes.addFlashAttribute("didUserAlreadyUpVoted", true);
    }
    return attributes;
  }

  private RedirectAttributes wasUserLoggedInAndDidAlreadyDownVoted(int id, RedirectAttributes attributes) {
    if (userService.getCurrentUser() == null) {
      attributes.addFlashAttribute("notLoggedInUserVoting", true);
    } else if (downVotePostService.wasPostDownVoted(id, userService.getCurrentUser().getId())) {
      attributes.addFlashAttribute("didUserAlreadyDownvoted", true);
    }
    return attributes;
  }
}
