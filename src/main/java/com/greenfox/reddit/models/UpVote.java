package com.greenfox.reddit.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity
public class UpVote {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private boolean wasUpvoted;

  @ManyToOne(fetch = FetchType.LAZY)
  private Post post;
  @ManyToOne
  private User user;
  @ManyToOne(fetch = FetchType.LAZY)
  private Comment comment;

  public UpVote() {
    this.wasUpvoted = false;
  }

  public UpVote(Post post, User user, Comment comment) {
    this.post = post;
    this.user = user;
    this.comment = comment;
    wasUpvoted = false;
  }
}
