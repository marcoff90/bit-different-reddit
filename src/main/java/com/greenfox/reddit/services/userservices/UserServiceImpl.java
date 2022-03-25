package com.greenfox.reddit.services.userservices;

import com.greenfox.reddit.models.User;
import com.greenfox.reddit.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private User currentUser;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void addUser(User user) {
    userRepository.save(user);
    currentUser = user;
  }

  @Override
  public User findUser(String userName, String password) {
    if (userRepository.findByUsernameAndPassword(userName, password) != null) {
      currentUser = userRepository.findByUsernameAndPassword(userName, password);
      return currentUser;
      // * checks for match of username and password, sets as current user
    } else {
      return null;
    }
  }

  @Override
  public User getCurrentUser() {
    return currentUser;
  }

  @Override
  public void logoutCurrentUser() {
    this.currentUser = null;
  }

  @Override
  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  @Override
  public boolean wasUserFound(String username, String password) {
    return userRepository.findByUsernameAndPassword(username, password) == null;
  }

}
