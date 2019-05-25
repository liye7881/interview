package com.vmvare.interview;

import com.vmvare.interview.handler.Handler;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubcribeContext {
  private static ThreadLocal<SubcribeContext> local = new ThreadLocal<>();

  private HttpServletRequest request;
  private HttpServletResponse response;
  private Handler handler;

  public SubcribeContext(HttpServletRequest request, HttpServletResponse response) {
    this.request = request;
    this.response = response;
  }

  public static SubcribeContext getContext() {
    return local.get();
  }

  public static void setContext(SubcribeContext context) {
    local.set(context);
  }

  public HttpServletRequest getRequest() {
    return request;
  }

  public HttpServletResponse getResponse() {
    return response;
  }

  public void setHandler(Handler handler) {
    this.handler = handler;
  }

  public Handler getHandler() {
    return handler;
  }
}
