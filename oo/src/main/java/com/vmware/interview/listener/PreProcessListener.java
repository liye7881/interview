package com.vmware.interview.listener;

import com.vmware.interview.DrawUtils;

public class PreProcessListener implements ProcessListener {

  private static ProcessListener INSTANCE = new PreProcessListener();

  private PreProcessListener() {
  }

  public static ProcessListener getInstance() {
    return INSTANCE;
  }


  @Override
  public void invoke() {
    DrawUtils.preProcess();
  }
}
