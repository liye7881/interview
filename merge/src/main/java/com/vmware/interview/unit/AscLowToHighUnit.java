package com.vmware.interview.unit;

import java.util.concurrent.CountDownLatch;

public class AscLowToHighUnit extends AbstractMergeUnit implements MergeUnit {

  public AscLowToHighUnit(CountDownLatch latch) {
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
        merged[index.getAndIncrement()] = one[oneIndex++];
      } else {
        merged[index.getAndIncrement()] = two[twoIndex++];
      }
    }
  }

}
