package com.vmware.interview;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unchecked")
public class MergeUnit implements Runnable {
  private CountDownLatch latch;

  private Comparable[] one;
  private Comparable[] two;

  private Comparable[] merged;

  private AtomicInteger index;
  private AtomicInteger otherIndex;

  private int oneIndex;
  private int twoIndex;

  private boolean lowToHigh = false;
  private boolean asc = true;

  public MergeUnit(CountDownLatch latch) {
    this.latch = latch;
  }

  @Override
  public void run() {
    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    while ((lowToHigh && index.get() <= otherIndex.get() ||
        (!lowToHigh && index.get() >= otherIndex.get()))) {
      if (oneIndex > one.length -1 || oneIndex < 0 ||
          twoIndex > two.length -1 || twoIndex < 0) {
        break;
      }
      if (lowToHigh) {
        if (asc) {
          if (one[oneIndex].compareTo(two[twoIndex]) < 0) {
            merged[index.getAndIncrement()] = one[oneIndex++];
          } else {
            merged[index.getAndIncrement()] = two[twoIndex++];
          }
        } else {
          if (one[oneIndex].compareTo(two[twoIndex]) < 0) {
            merged[index.getAndIncrement()] = two[twoIndex++];
          } else {
            merged[index.getAndIncrement()] = one[oneIndex++];
          }
        }
      } else {
        if (asc) {
          if (one[oneIndex].compareTo(two[twoIndex]) < 0) {
            merged[index.getAndDecrement()] = two[twoIndex--];
          } else {
            merged[index.getAndDecrement()] = one[oneIndex--];
          }
        } else {
          if (one[oneIndex].compareTo(two[twoIndex]) < 0) {
            merged[index.getAndDecrement()] = one[oneIndex--];
          } else {
            merged[index.getAndDecrement()] = two[twoIndex--];
          }
        }
      }
    }
  }

  public void setOne(Comparable<Object>[] one) {
    this.one = one;
  }

  public void setTwo(Comparable<Object>[] two) {
    this.two = two;
  }

  public void setMerged(Comparable<Object>[] merged) {
    this.merged = merged;
  }

  public void setIndex(AtomicInteger index) {
    this.index = index;
  }

  public void setOtherIndex(AtomicInteger otherIndex) {
    this.otherIndex = otherIndex;
  }

  public void setOneIndex(int oneIndex) {
    this.oneIndex = oneIndex;
  }

  public void setTwoIndex(int twoIndex) {
    this.twoIndex = twoIndex;
  }

  public void setLowToHigh(boolean lowToHigh) {
    this.lowToHigh = lowToHigh;
  }
}
