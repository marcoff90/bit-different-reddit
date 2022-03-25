package com.greenfox.reddit.repositories;

import com.greenfox.reddit.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  User findByUsernameAndPassword(String userName, String password);

  User findByUsername(String username);

}
