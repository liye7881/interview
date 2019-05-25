package com.vmvare.interview.handler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class HandlerFactory {
  private static ConcurrentMap<String, Handler> handlers = new ConcurrentHashMap<>();

  public static Handler getHandler(String url) {
    Handler handler = handlers.get(url);
    if (handler == null) {
      handler = DefaultHandler.INSTANCE;
    }
    return handler;
  }

  public static void setHandler(String url, Handler handler) {
    handlers.putIfAbsent(url, handler);
  }
}
