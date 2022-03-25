package com.greenfox.reddit.controllers;

import com.greenfox.reddit.services.commentservices.CommentService;
import com.greenfox.reddit.services.downvoteservices.DownVoteService;
import com.greenfox.reddit.services.postservices.PostService;
import com.greenfox.reddit.services.userservices.UserService;
import com.greenfox.reddit.services.voteservices.UpVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CommentsController {

  private UserService userService;
  private PostService postService;
  private CommentService commentService;
  private UpVoteService upVoteService;
  private DownVoteService downVoteService;

  @Autowired
  public CommentsController(UserService userService, PostService postService,
      CommentService commentService, UpVoteService upVoteService,
      DownVoteService downVoteService) {
    this.userService = userService;
    this.postService = postService;
    this.commentService = commentService;
    this.upVoteService = upVoteService;
    this.downVoteService = downVoteService;
  }

  @GetMapping("/{id}/comments")
  public String showComments(@PathVariable int id, Model model) {
    model.addAttribute("user", userService.getCurrentUser()); // * renders username
    model.addAttribute("post", postService.findById(id));   // * shows only post which was chosen
    model.addAttribute("topThreeComments", commentService.findTopThree(id)); // * loads topThree comments related to the post
    model.addAttribute("otherComments", commentService.findAllAfterTopThree(id));
    doesModelContainsAttributes(model);
    return "reddit/comments";
  }

  @PostMapping("/{id}/comments")
  public String storeComment(@PathVariable int id, @RequestParam String description, RedirectAttributes attributes) {
    if (commentService.addComment(description, id)) {
      attributes.addFlashAttribute("wasCommentAdded", true);
    }
    return "redirect:/" + id + "/comments";
  }

  @GetMapping("/{postId}/{commentId}/upvote")
  public String upvoteComment(@PathVariable int postId, @PathVariable int commentId, Model model, RedirectAttributes attributes) {
    model.addAttribute("user", userService.getCurrentUser());
    if (userService.getCurrentUser() != null && upVoteService.findByPostIdAndUserIdAndCommentId(postId,
        userService.getCurrentUser().getId(), commentId) == null) {

      upVoteService.addUpVoteForComment(postService.findById(postId), userService.getCurrentUser(), commentService.findById(commentId));
      commentService.upVoteComment(postId, commentId);

    } else {
      wasUserLoggedInAndDidAlreadyUpvotedComment(postId, commentId, attributes);
    } // * similar logic as for post voting
    return "redirect:/" + postId + "/comments";
  }

  @GetMapping("/{postId}/{commentId}/downvote")
  public String downVoteComment(@PathVariable int postId, @PathVariable int commentId, Model model, RedirectAttributes attributes) {
    model.addAttribute("user", userService.getCurrentUser());
    if (userService.getCurrentUser() != null
        && downVoteService.findByPostIdAndUserIdAndCommentId(postId, userService.getCurrentUser().getId(), commentId) == null) {
      downVoteService.addUpDownVoteForComment(postService.findById(postId), userService.getCurrentUser(),
          commentService.findById(commentId));
      commentService.downVoteComment(postId, commentId);
    } else {
      wasUserLoggedInAndDidAlreadyDownVotedComment(postId, commentId, attributes);
    }
    return "redirect:/" + postId + "/comments";
  }

  private RedirectAttributes wasUserLoggedInAndDidAlreadyUpvotedComment(int postId, int commentId, RedirectAttributes attributes) {
    if (userService.getCurrentUser() == null) {
      attributes.addFlashAttribute("notLoggedInUserVoting", true);
    } else if (upVoteService.wasCommentUpVoted(postId, userService.getCurrentUser().getId(), commentId)) {
      attributes.addFlashAttribute("didUserAlreadyUpVoted", true);
    }
    return attributes;
  }

  private RedirectAttributes wasUserLoggedInAndDidAlreadyDownVotedComment(int postId, int commentId, RedirectAttributes attributes) {
    if (userService.getCurrentUser() == null) {
      attributes.addFlashAttribute("notLoggedInUserVoting", true);
    } else if (downVoteService.wasCommentDownVoted(postId, userService.getCurrentUser().getId(), commentId)) {
      attributes.addFlashAttribute("didUserAlreadyDownvoted", true);
    }
    return attributes;
  }

  private Model doesModelContainsAttributes(Model model) {
    if (model.containsAttribute("wasCommentAdded")) {
      model.addAttribute("wasCommentAdded", model.getAttribute("wasCommentAdded"));
    } else if (model.containsAttribute("didUserAlreadyUpVoted")) {
      model.addAttribute("didUserAlreadyUpVoted", model.getAttribute("didUserAlreadyUpVoted"));
    } else if (model.containsAttribute("didUserAlreadyDownvoted")) {
      model.addAttribute("didUserAlreadyDownvoted", model.getAttribute("didUserAlreadyDownvoted"));
    } else if (model.containsAttribute("notLoggedInUserVoting")) {
      model.addAttribute("notLoggedInUserVoting", model.getAttribute("notLoggedInUserVoting"));
    }
    return model;
  }
}
