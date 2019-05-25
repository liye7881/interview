package com.vmvare.interview;

public class SubscribeException extends RuntimeException {

  public SubscribeException(String msg) {
    super(msg);
  }

  public SubscribeException(Throwable cause) {
    super(cause);
  }
}
