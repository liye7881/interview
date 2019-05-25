package com.vmvare.interview;

import com.vmvare.interview.handler.Handler;
import com.vmvare.interview.handler.HandlerFactory;
import com.vmvare.interview.handler.OperUserServiceHandler;
import com.vmvare.interview.handler.ServiceHandler;
import com.vmvare.interview.handler.UserHandler;
import com.vmvare.interview.handler.UserServiceHandler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/subscribe/*")
public class DispatchServlet extends HttpServlet {
  private final static String PREFIX = "/subscribe/";

  @Override
  public void init() throws ServletException {
    HandlerFactory.setHandler("user", new UserHandler());
    HandlerFactory.setHandler("service", new ServiceHandler());
    HandlerFactory.setHandler("user-service", new UserServiceHandler());
    HandlerFactory.setHandler("oper-user-service", new OperUserServiceHandler());
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    Handler handler = getHandler(req);
    if (handler != null) {
      handler.handleGet(req, resp);
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    Handler handler = getHandler(req);
    if (handler != null) {
      handler.handlePost(req, resp);
    }
  }

  private Handler getHandler(HttpServletRequest req) {
    String requestURI = req.getRequestURI();
    String contextPath = req.getContextPath();

    String handleURL = requestURI.substring(contextPath.length()).substring(PREFIX.length());

    return HandlerFactory.getHandler(handleURL);
  }
}
