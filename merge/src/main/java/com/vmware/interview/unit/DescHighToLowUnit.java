package com.vmware.interview.unit;

import java.util.concurrent.CountDownLatch;

public class DescHighToLowUnit extends AbstractMergeUnit implements MergeUnit {

  public DescHighToLowUnit(CountDownLatch latch) {
    super(latch);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void merge() {
    while (indexNotLess()) {
      if (outofScope()) {
        break;
      }

      if (one[oneIndex].compareTo(two[twoIndex]) < 0) {
        merged[index.getAndDecrement()] = one[oneIndex--];
      } else {
        merged[index.getAndDecrement()] = two[twoIndex--];
      }
    }
  }

}
