package com.vmvare.interview;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.stream.IntStream;

public class Writer implements Callable<Integer> {
  private MemQueue queue;

  private Random random = new Random();

  public Writer(MemQueue queue) {
    this.queue = queue;
  }

  @Override
  public Integer call() throws Exception {
    int send = 9000 + random.nextInt(1000);

    String prefix = Thread.currentThread().getName();
    IntStream.range(0, send).forEach(i -> {
      queue.push(prefix + " " + i);
    });

    return send;
  }
}
