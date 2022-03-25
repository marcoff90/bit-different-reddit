package com.greenfox.reddit.controllers;

import com.greenfox.reddit.models.User;
import com.greenfox.reddit.services.postservices.PostService;
import com.greenfox.reddit.services.userservices.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

  private UserService userService;
  private PostService postService;

  @Autowired
  public UserController(UserService userService, PostService postService) {
    this.userService = userService;
    this.postService = postService;
  }

  @GetMapping("/register")
  public String showRegistration(Model model) {
    model.addAttribute("user", userService.getCurrentUser());
    if (model.containsAttribute("existingUsername")) {
      model.addAttribute("existingUsername", model.getAttribute("existingUsername")); // * alert showing
    }
    return "reddit/register";
  }

  @PostMapping("/register")
  public String storeUser(@ModelAttribute User user, Model model, RedirectAttributes attributes) {
    if (userService.findByUsername(user.getUsername()) == null) {
      userService.addUser(user);
      model.addAttribute("user", userService.getCurrentUser());
      // * if username isn't found, the user is created
      attributes.addFlashAttribute("userCreated", true);
      return "redirect:/";
    } else {
      attributes.addFlashAttribute("existingUsername", true);
      return "redirect:/register";
    }
  }

  @GetMapping("/login")
  public String showLoginPage(Model model) {
    model.addAttribute("user", userService.getCurrentUser());
    if (model.containsAttribute("wasUserFound")) {
      model.addAttribute("wasUserFound", model.getAttribute("wasUserFound"));
    }
    return "reddit/login";
  }

  @PostMapping("/login")
  public String getLogin(@RequestParam String username, @RequestParam String password, Model model, RedirectAttributes attributes) {
    model.addAttribute("user", userService.getCurrentUser());
    if (userService.findUser(username, password) != null) {
      attributes.addFlashAttribute("wasUserFound", !userService.wasUserFound(username, password));
      return "redirect:/";
    } else {
      attributes.addFlashAttribute("wasUserFound", userService.wasUserFound(username, password));
      return "redirect:/login";
    }
  }

  @GetMapping("/logout")
  public String logout(Model model, RedirectAttributes attributes) {
    model.addAttribute("user", userService.getCurrentUser());
    if (userService.getCurrentUser() != null) {
      attributes.addFlashAttribute("loggedOutUser", true);
    }
    userService.logoutCurrentUser();
    return "redirect:/";
  }
}
