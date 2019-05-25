package com.vmware.interview;

public class DefaultDrawable extends AbstractDrawable {
  private String type;

  public DefaultDrawable(String type) {
    this.type = type;
  }

  @Override
  protected void doDraw() {
    System.out.println("Draw " + type);
  }
}
