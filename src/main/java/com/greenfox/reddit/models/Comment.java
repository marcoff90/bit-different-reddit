package com.greenfox.reddit.models;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String description;
  private int votes;

  @Temporal(TemporalType.TIMESTAMP)
  private Date date;

  @ManyToOne
  private User user;
  @ManyToOne
  private Post post;
  @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
  private List<UpVote> upvotes;
  @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL)
  private List<DownVote> downVotes;

  public Comment(String description) {
    this.description = description;
    this.votes = 0;
    this.date = new Date();
  }

  public Comment() {
    this.votes = 0;
    this.date = new Date();
  }

  public String getElapsedTime() {
    Date current = new Date();
    Date diff = new Date(current.getTime() - date.getTime());
    long hours = diff.getHours() * 59 / 60;
    long minutes = diff.getMinutes();
    long seconds = diff.getSeconds();
    return hours == 0 ? minutes + " minutes ago..." : hours + " hours and " + minutes + " minutes ago...";
  }

  public String getUserName() {
    return user == null ? "Anonymous" : user.getUsername();
  }

}
