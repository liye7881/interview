package com.vmware.interview;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class Merger {
  private ExecutorService executorService;

  public Merger(ExecutorService executorService) {
    this.executorService = executorService;
  }


  @SuppressWarnings("unchecked")
  public Comparable[] merge(Comparable[] one, Comparable[] two, boolean asc) {
    if (one != null && two != null) {
      if (one.length != 0 && two.length != 0) {

      } else {
        return one.length == 0 ? two : one;
      }
    } else {
      return one == null ? two : one;
    }

    int mergedSize = one.length + two.length;

    AtomicInteger lowToHighIndex = new AtomicInteger(0);
    AtomicInteger highToLowIndex = new AtomicInteger(mergedSize - 1);

    CountDownLatch latch = new CountDownLatch(1);

    Comparable[] merged = new Comparable[mergedSize];

    MergeUnit lowToHighUnit = new MergeUnit(latch);
    lowToHighUnit.setOne(one);
    lowToHighUnit.setTwo(two);
    lowToHighUnit.setMerged(merged);
    lowToHighUnit.setIndex(lowToHighIndex);
    lowToHighUnit.setOtherIndex(highToLowIndex);
    lowToHighUnit.setOneIndex(0);
    lowToHighUnit.setTwoIndex(0);
    lowToHighUnit.setLowToHigh(true);

    MergeUnit highToLowUnit = new MergeUnit(latch);
    highToLowUnit.setOne(one);
    highToLowUnit.setTwo(two);
    highToLowUnit.setMerged(merged);
    highToLowUnit.setIndex(highToLowIndex);
    highToLowUnit.setOtherIndex(lowToHighIndex);
    highToLowUnit.setOneIndex(one.length - 1);
    highToLowUnit.setTwoIndex(two.length - 1);
    highToLowUnit.setLowToHigh(false);

    Future<?> lowToHigh = executorService.submit(lowToHighUnit);
    Future<?> highToLow = executorService.submit(highToLowUnit);

    latch.countDown();

    try {
      lowToHigh.get();
      highToLow.get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

    return merged;
  }

  public byte[] merge(byte[] one, byte[] two, boolean asc) {
    Comparable[] oneC = getComparables(one);
    Comparable[] twoC = getComparables(two);

    Comparable[] mergedC = merge(oneC, twoC, asc);
    byte[] merged = new byte[mergedC.length];
    for (int i = 0; i < mergedC.length; i++) {
      merged[i] = (Byte) mergedC[i];
    }

    return merged;
  }

  public boolean[] merge(boolean[] one, boolean[] two, boolean asc) {
    Comparable[] oneC = getComparables(one);
    Comparable[] twoC = getComparables(two);

    Comparable[] mergedC = merge(oneC, twoC, asc);
    boolean[] merged = new boolean[mergedC.length];
    for (int i = 0; i < mergedC.length; i++) {
      merged[i] = (Boolean) mergedC[i];
    }

    return merged;
  }


  public char[] merge(char[] one, char[] two, boolean asc) {
    Comparable[] oneC = getComparables(one);
    Comparable[] twoC = getComparables(two);

    Comparable[] mergedC = merge(oneC, twoC, asc);
    char[] merged = new char[mergedC.length];
    for (int i = 0; i < mergedC.length; i++) {
      merged[i] = (Character) mergedC[i];
    }

    return merged;
  }

  public short[] merge(short[] one, short[] two, boolean asc) {
    Comparable[] oneC = getComparables(one);
    Comparable[] twoC = getComparables(two);

    Comparable[] mergedC = merge(oneC, twoC, asc);
    short[] merged = new short[mergedC.length];
    for (int i = 0; i < mergedC.length; i++) {
      merged[i] = (Short) mergedC[i];
    }

    return merged;
  }

  public int[] merge(int[] one, int[] two, boolean asc) {
    Comparable[] oneC = getComparables(one);
    Comparable[] twoC = getComparables(two);

    Comparable[] mergedC = merge(oneC, twoC, asc);
    int[] merged = new int[mergedC.length];
    for (int i = 0; i < mergedC.length; i++) {
      merged[i] = (Integer) mergedC[i];
    }

    return merged;
  }

  public long[] merge(long[] one, long[] two, boolean asc) {
    Comparable[] oneC = getComparables(one);
    Comparable[] twoC = getComparables(two);

    Comparable[] mergedC = merge(oneC, twoC, asc);
    long[] merged = new long[mergedC.length];
    for (int i = 0; i < mergedC.length; i++) {
      merged[i] = (Long) mergedC[i];
    }

    return merged;
  }

  public float[] merge(float[] one, float[] two, boolean asc) {
    Comparable[] oneC = getComparables(one);
    Comparable[] twoC = getComparables(two);

    Comparable[] mergedC = merge(oneC, twoC, asc);
    float[] merged = new float[mergedC.length];
    for (int i = 0; i < mergedC.length; i++) {
      merged[i] = (Float) mergedC[i];
    }

    return merged;
  }

  public double[] merge(double[] one, double[] two, boolean asc) {
    Comparable[] oneC = getComparables(one);
    Comparable[] twoC = getComparables(two);

    Comparable[] mergedC = merge(oneC, twoC, asc);
    double[] merged = new double[mergedC.length];
    for (int i = 0; i < mergedC.length; i++) {
      merged[i] = (Double) mergedC[i];
    }

    return merged;
  }

  private Comparable[] getComparables(byte[] one) {
    Comparable[] oneC = new Comparable[one.length];
    for (int i = 0; i < one.length; i++) {
      oneC[i] = one[i];
    }
    return oneC;
  }


  private Comparable[] getComparables(char[] one) {
    Comparable[] oneC = new Comparable[one.length];
    for (int i = 0; i < one.length; i++) {
      oneC[i] = one[i];
    }
    return oneC;
  }

  private Comparable[] getComparables(boolean[] one) {
    Comparable[] oneC = new Comparable[one.length];
    for (int i = 0; i < one.length; i++) {
      oneC[i] = one[i];
    }
    return oneC;
  }

  private Comparable[] getComparables(short[] one) {
    Comparable[] oneC = new Comparable[one.length];
    for (int i = 0; i < one.length; i++) {
      oneC[i] = one[i];
    }
    return oneC;
  }

  private Comparable[] getComparables(long[] one) {
    Comparable[] oneC = new Comparable[one.length];
    for (int i = 0; i < one.length; i++) {
      oneC[i] = one[i];
    }
    return oneC;
  }

  private Comparable[] getComparables(int[] one) {
    Comparable[] oneC = new Comparable[one.length];
    for (int i = 0; i < one.length; i++) {
      oneC[i] = one[i];
    }
    return oneC;
  }

  private Comparable[] getComparables(float[] one) {
    Comparable[] oneC = new Comparable[one.length];
    for (int i = 0; i < one.length; i++) {
      oneC[i] = one[i];
    }
    return oneC;
  }

  private Comparable[] getComparables(double[] one) {
    Comparable[] oneC = new Comparable[one.length];
    for (int i = 0; i < one.length; i++) {
      oneC[i] = one[i];
    }
    return oneC;
  }
}
