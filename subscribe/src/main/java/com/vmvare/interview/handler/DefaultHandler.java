package com.vmvare.interview.handler;

import com.vmvare.interview.Constants;
import com.vmvare.interview.Utils;
import com.vmvare.interview.bean.Service;
import com.vmvare.interview.bean.User;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

  protected int getPageStart(HttpServletRequest request) {
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

  protected int getPageSize() {
    return 5;
  }

  protected DataSource getDataSource(HttpServletRequest request) {
    return (DataSource) request.getServletContext().getAttribute(Constants.DATASOURCE);
  }

  protected List<User> getUsersFromResultSet(ResultSet set) throws SQLException {
    List<User> users = new ArrayList<>();
    while (set.next()) {
      User user = new User();
      user.setUserId(set.getInt(1));
      user.setName(set.getString(2));
      user.setVersion(set.getInt(3));

      users.add(user);
    }
    return users;
  }

  protected List<Service> getServicesForResultSet(ResultSet set) throws SQLException {
    List<Service> services = new ArrayList<>();
    while (set.next()) {
      Service service = new Service();
      service.setServiceId(set.getInt(1));
      service.setName(set.getString(2));
      service.setVersion(set.getInt(3));

      services.add(service);
    }
    return services;
  }

  private static class Inner {
    public static final Handler handler = new DefaultHandler();
  }
}
