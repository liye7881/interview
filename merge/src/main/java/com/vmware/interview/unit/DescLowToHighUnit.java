package com.vmware.interview.unit;

import java.util.concurrent.CountDownLatch;

public class DescLowToHighUnit extends AbstractMergeUnit implements MergeUnit {

  public DescLowToHighUnit(CountDownLatch latch) {
    super(latch);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void merge() {
    while (indexNotGreater()) {
      if (outofScope()) {
        break;
      }

      if (one[oneIndex].compareTo(two[twoIndex]) < 0) {
        merged[index.getAndIncrement()] = two[twoIndex++];
      } else {
        merged[index.getAndIncrement()] = one[oneIndex++];
      }
    }
  }
}
