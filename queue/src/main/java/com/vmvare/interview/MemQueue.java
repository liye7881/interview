package com.vmvare.interview;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

public class MemQueue {
  private static Logger LOGGER = Logger.getLogger(MemQueue.class.getName());

  private Queue<String> queue = new LinkedList<>();

  public volatile boolean closed = false;

  private Lock lock = new ReentrantLock(true);

  private Condition read  = lock.newCondition();
  private Condition write = lock.newCondition();

  public void push(String s) {
    try {
      lock.lock();

      while (queue.size() > 1000 && !closed) {
        write.await(1l, TimeUnit.MICROSECONDS);
      }

      if (!closed) {
        queue.offer(s);

        read.signalAll();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

  public String pop() {
    String pop = null;
    try {
      lock.lock();

      while (queue.size() == 0 && !closed) {
        read.await(1l, TimeUnit.MICROSECONDS);
      }

      if (!closed) {
        pop = queue.poll();

        write.signalAll();
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }

    return pop;
  }

  public int size() {
    try {
      lock.lock();
      return queue.size();
    } finally {
      lock.unlock();
    }
  }
}
