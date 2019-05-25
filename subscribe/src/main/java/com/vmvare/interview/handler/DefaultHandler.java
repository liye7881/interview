package com.vmvare.interview.handler;

import com.vmvare.interview.Constants;
import com.vmvare.interview.Utils;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class DefaultHandler implements Handler {
  public static final Handler INSTANCE = Inner.handler;

  @Override
  public void handleGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    forwardToDefault(request, response);
  }

  @Override
  public void handlePost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    forwardToDefault(request, response);
  }

  protected void forwardToDefault(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.getRequestDispatcher("/default.jsp").forward(request, response);
  }

  protected String getOper(HttpServletRequest request) {
    String oper = request.getParameter("oper");
    if (oper == null) {
      oper = "list";
    }

    return oper;
  }

  @Override
  public int getPageStart(HttpServletRequest request) {
    return getPage(request) * getPageSize();
  }

  protected int getPageEnd(HttpServletRequest request) {
    return (getPage(request) + 1) * getPageSize();
  }

  private int getPage(HttpServletRequest request) {
    Integer page = Utils.getIntParams(request, "page");
    if (page == null) {
      page = 0;
    }else if (page < 0) {
      page = 0;
    }

    request.setAttribute("page", page);

    return page;
  }

  @Override
  public int getPageSize() {
    return 5;
  }

  protected DataSource getDataSource(HttpServletRequest request) {
    return (DataSource) request.getServletContext().getAttribute(Constants.DATASOURCE);
  }

  private static class Inner {
    public static final Handler handler = new DefaultHandler();
  }
}
