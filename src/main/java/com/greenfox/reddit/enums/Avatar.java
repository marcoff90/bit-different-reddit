package com.greenfox.reddit.enums;

public enum Avatar {

  DEFAULT_AVATAR("/img/avatars/user.png");

  public final String label;

  private Avatar(String label) {
    this.label = label;
  }


}
