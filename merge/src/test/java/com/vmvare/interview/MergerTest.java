package com.vmvare.interview;

import com.vmware.interview.Merger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import org.junit.Assert;
import org.junit.Test;

public class MergerTest {
  @Test
  @SuppressWarnings("unchecked")
  public void testMerge() {
    final int count = 2000000;

    int[] one = new int[count / 2];
    int[] two = new int[count / 2];

    IntStream.range(0, count).forEach(i -> {
      if (i % 2 == 0) {
        one[i / 2] = i;
      } else {
        two[i / 2] = i;
      }
    });

    ExecutorService service = Executors.newFixedThreadPool(2);

    Merger merger = new Merger(service);
    int[] ascMerged = merger.merge(one, two, true);

    IntStream.range(0, count).forEach(i -> {
      Assert.assertEquals(ascMerged[i], i);
    });

    IntStream.range(count, 0).forEach(i -> {
      int opposite = Math.abs(i - count);
      if (opposite % 2 == 0) {
        one[opposite / 2] = i;
      } else {
        two[opposite / 2] = i;
      }
    });

    int[] descMerged = merger.merge(one, two, false);
    IntStream.range(count, 0).forEach(i -> {
      int opposite = Math.abs(i - count);
      Assert.assertEquals(descMerged[opposite / 2], i);
    });

    service.shutdown();
  }
}
