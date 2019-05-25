package com.vmware.interview;

import com.vmvare.interview.MemQueue;
import com.vmvare.interview.Reader;
import com.vmvare.interview.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class MemQueueTest extends TestCase {
  @Test
  public void testQueme() {
    ExecutorService service = Executors.newFixedThreadPool(20);
    List<Future<Integer>> writers = new ArrayList<>();
    List<Future<Integer>> readers = new ArrayList<>();

    AtomicInteger write = new AtomicInteger(0);
    AtomicInteger read  = new AtomicInteger(0);

    MemQueue queue = new MemQueue();

    IntStream.range(0, 10).forEach(i -> {
      writers.add(service.submit(new Writer(queue)));
      readers.add(service.submit(new Reader(queue)));
    });

    writers.forEach(writer -> {
      try {
        write.addAndGet(writer.get());
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    try {
      while (queue.size() != 0) {
        TimeUnit.MILLISECONDS.sleep(1);
      }

      queue.closed = true;
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    readers.forEach(reader -> {
      try {
        read.addAndGet(reader.get());
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

    Assert.assertEquals(read.get(), write.get());

    service.shutdown();
  }
}
