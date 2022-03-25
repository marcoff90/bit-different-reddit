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
public class UpVotePost {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private boolean wasUpvoted;

  @ManyToOne(fetch = FetchType.LAZY)
  private Post post;
  @ManyToOne
  private User user;

  public UpVotePost() {
    this.wasUpvoted = false;
  }

  public UpVotePost(Post post, User user) {
    this.post = post;
    this.user = user;
    wasUpvoted = false;
  }
}
