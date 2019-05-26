package com.vmware.interview.unit;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractMergeUnit implements MergeUnit {

  private CountDownLatch latch;

  protected Comparable[] one;
  protected Comparable[] two;

  protected Comparable[] merged;

  protected AtomicInteger index;
  protected AtomicInteger otherIndex;

  protected int oneIndex;
  protected int twoIndex;

  public AbstractMergeUnit(CountDownLatch latch) {
    this.latch = latch;
  }

  @Override
  public void run() {
    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    merge();
  }

  @Override
  public void setOne(Comparable<Object>[] one) {
    this.one = one;
  }

  @Override
  public void setTwo(Comparable<Object>[] two) {
    this.two = two;
  }

  @Override
  public void setMerged(Comparable<Object>[] merged) {
    this.merged = merged;
  }

  @Override
  public void setIndex(AtomicInteger index) {
    this.index = index;
  }

  @Override
  public void setOtherIndex(AtomicInteger otherIndex) {
    this.otherIndex = otherIndex;
  }

  @Override
  public void setOneIndex(int oneIndex) {
    this.oneIndex = oneIndex;
  }

  @Override
  public void setTwoIndex(int twoIndex) {
    this.twoIndex = twoIndex;
  }

  protected boolean outofScope() {
    return oneIndex > one.length - 1 || oneIndex < 0 ||
        twoIndex > two.length - 1 || twoIndex < 0;
  }

  protected boolean indexNotGreater() {
    return index.get() <= otherIndex.get();
  }

  protected boolean indexNotLess() {
    return index.get() >= otherIndex.get();
  }
}
