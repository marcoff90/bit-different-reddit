package com.greenfox.reddit.services.userservices;

import com.greenfox.reddit.models.User;

public interface UserService {

  void addUser(User user);

  User findUser(String userName, String password);

  User getCurrentUser();

  void logoutCurrentUser();

  User findByUsername(String username);

  boolean wasUserFound(String username, String password);

}
