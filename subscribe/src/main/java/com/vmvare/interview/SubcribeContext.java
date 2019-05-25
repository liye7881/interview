package com.vmvare.interview;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SubcribeContext {
  private static ThreadLocal<SubcribeContext> local = new ThreadLocal<>();

  private HttpServletRequest request;
  private HttpServletResponse response;

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
}
