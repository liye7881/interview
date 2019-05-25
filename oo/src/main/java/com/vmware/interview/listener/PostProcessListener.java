package com.vmware.interview.listener;

import com.vmware.interview.DrawUtils;

public class PostProcessListener implements ProcessListener {

  private static ProcessListener INSTANCE = new PostProcessListener();

  private PostProcessListener() {
  }

  public static ProcessListener getInstance() {
    return INSTANCE;
  }

  @Override
  public void invoke() {
    DrawUtils.postProcess();
  }
}
