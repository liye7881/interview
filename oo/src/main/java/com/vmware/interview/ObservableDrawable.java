package com.vmware.interview;

import com.vmware.interview.listener.ProcessListener;

public class ObservableDrawable implements Drawable {

  private ProcessListener preListener;
  private ProcessListener postListener;

  public ObservableDrawable(ProcessListener preListener,
      ProcessListener postListener) {
    this.preListener = preListener;
    this.postListener = postListener;
  }

  @Override
  public void draw() {
    if (preListener != null) {
      preListener.invoke();
    }

    System.out.println("Draw observer");

    if (postListener != null) {
      postListener.invoke();
    }
  }
}
