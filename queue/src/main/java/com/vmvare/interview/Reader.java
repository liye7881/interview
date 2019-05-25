package com.vmvare.interview;

import java.util.concurrent.Callable;

public class Reader implements Callable<Integer> {
  private MemQueue queue;

  public Reader(MemQueue queue) {
    this.queue = queue;
  }

  @Override
  public Integer call() throws Exception {
    int receive = 0;

    while (!queue.closed) {
      if (queue.pop() != null) {
        receive++;
      }
    }

    return receive;
  }
}
