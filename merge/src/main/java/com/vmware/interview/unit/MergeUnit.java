package com.vmware.interview.unit;

import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unchecked")
public interface MergeUnit extends Runnable {

  void merge();

  void setOne(Comparable<Object>[] one);

  void setTwo(Comparable<Object>[] two);

  void setMerged(Comparable<Object>[] merged);

  void setIndex(AtomicInteger index);

  void setOtherIndex(AtomicInteger otherIndex);

  void setOneIndex(int oneIndex);

  void setTwoIndex(int twoIndex);
}
