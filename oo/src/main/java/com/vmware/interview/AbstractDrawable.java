package com.vmware.interview;

public abstract class AbstractDrawable implements Drawable {

  protected void preProcessing() {
    DrawUtils.preProcess();
  }

  public void draw() {
    preProcessing();

    doDraw();

    postProcessing();
  }

  protected void postProcessing() {
    DrawUtils.postProcess();
  }

  protected abstract void doDraw();
}
