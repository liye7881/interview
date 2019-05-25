package com.vmvare.interview.bean;

public class UserService {
  private int relaId;
  private User user;
  private Service service;

  public int getRelaId() {
    return relaId;
  }

  public void setRelaId(int relaId) {
    this.relaId = relaId;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Service getService() {
    return service;
  }

  public void setService(Service service) {
    this.service = service;
  }
}
