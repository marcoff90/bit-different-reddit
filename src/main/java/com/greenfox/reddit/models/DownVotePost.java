package com.greenfox.reddit.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class DownVotePost {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private boolean wasDownVoted;

  @ManyToOne(fetch = FetchType.LAZY)
  private Post post;
  @ManyToOne
  private User user;

  public DownVotePost() {
    this.wasDownVoted = false;
  }

  public DownVotePost(Post post, User user) {
    this.post = post;
    this.user = user;
    wasDownVoted = false;
  }
}
