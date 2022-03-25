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
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String url;
  private String description;
  private int votes;
  @ManyToOne
  private User user;
  @Temporal(TemporalType.TIMESTAMP)
  private Date date;
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  private List<UpVotePost> votesList;
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
  private List<DownVotePost> downVotes;
  @OneToMany(mappedBy = "post")
  private List<Comment> comments;

  public Post(String url, String description) {
    this.url = url;
    this.description = description;
    this.date = new Date();
    this.votes = 0;
  }

  public Post() {
    this.date = new Date();
    this.votes = 0;
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
