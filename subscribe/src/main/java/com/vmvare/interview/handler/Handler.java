package com.vmvare.interview.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Handler {
  void handleGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException;

  void handlePost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException;

  int getPageStart(HttpServletRequest request);

  int getPageSize();
}
