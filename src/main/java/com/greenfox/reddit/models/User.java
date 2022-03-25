package com.greenfox.reddit.models;

import com.greenfox.reddit.enums.Avatar;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import lombok.Data;

@Entity
@Data
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String username;
  private String password;
  private String avatar;
  @OneToMany(mappedBy = "user")
  private List<Post> posts;
  @OneToMany(mappedBy = "user")
  private List<UpVote> upVotes;
  @OneToMany(mappedBy = "user")
  private List<DownVote> downVotes;
  @OneToMany(mappedBy = "user")
  private List<UpVotePost> upVotesPost;
  @OneToMany(mappedBy = "user")
  private List<DownVotePost> downVotesPost;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public User() {
  }

  public String getAvatar() {
    return avatar == null ? Avatar.DEFAULT_AVATAR.label : avatar;
  }
}
