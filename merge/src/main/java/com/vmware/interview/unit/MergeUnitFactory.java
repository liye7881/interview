package com.vmware.interview.unit;

import java.util.concurrent.CountDownLatch;

public class MergeUnitFactory {
  public static MergeUnit newInstance(boolean asc, boolean lowToHigh, CountDownLatch latch) {
    MergeUnit unit;

    if (asc) {
      if (lowToHigh) {
        unit = new AscLowToHighUnit(latch);
      } else {
        unit = new AscHighToLowUnit(latch);
      }
    } else {
      if (lowToHigh) {
        unit = new DescLowToHighUnit(latch);
      } else  {
        unit = new DescHighToLowUnit(latch);
      }
    }

    return unit;
  }
}
