package com.vmware.interview;

public class NothingToDraw implements Drawable {
  private static Drawable INSTANCE = Inner.INSTANCE;

  private NothingToDraw() {

  }

  public static final Drawable getInstance() {
    return INSTANCE;
  }

  @Override
  public void draw() {
    System.out.println("Nothing to draw");
  }

  static class Inner {
    static Drawable INSTANCE = new NothingToDraw();
  }
}
