package com.vmvare.interview.handler;

import com.vmvare.interview.Utils;
import com.vmvare.interview.bean.Service;
import com.vmvare.interview.bean.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class OperUserServiceHandler extends DefaultHandler {
  public static final String USER_SQL = "select a.user_id, a.name, a.version from t_users a "
      + "where not exists (select * from t_user_service_rela b where a.user_id = b.user_id and b.service_id = ?) o"
      + "rder by a.user_id desc limit ?, ?";
  public static final String SERVICE_SQL = "select a.service_id, a.name, a.version from t_services a "
      + "where not exists (select * from t_user_service_rela b where a.service_id = b.service_id and user_id = ?)"
      + " order by a.service_id desc limit ?, ?";

  @Override
  public void handleGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String oper = getOper(request);
    switch (oper) {
      case "add":
        doGetAdd(request, response);
        break;
      case "del":
        doGetDel(request, response);
        break;
      default:
        forwardToDefault(request, response);
    }
  }

  @Override
  public void handlePost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String oper = getOper(request);
    switch (oper) {
      case "add":
        doPostAdd(request, response);
        break;
    }
  }

  private void doGetAdd(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    int pageStart = getPageStart(request);
    int pageSize  = getPageSize();

    Integer userId    = Utils.getIntParams(request, "user_id");
    Integer serviceId = Utils.getIntParams(request, "service_id");

    DataSource source = getDataSource(request);
    try
        (Connection conn = source.getConnection()) {
      if (userId != null) {
        PreparedStatement ps = conn.prepareStatement(SERVICE_SQL);
        ps.setInt(1, userId);
        ps.setInt(2, pageStart);
        ps.setInt(3, pageSize);

        List<Service> services = getServicesForResultSet(ps.executeQuery());

        request.setAttribute("services", services);
      } else if (serviceId != null){
        PreparedStatement ps = conn.prepareStatement(USER_SQL);
        ps.setInt(1, serviceId);
        ps.setInt(2, pageStart);
        ps.setInt(3, pageSize);

        List<User> users = getUsersFromResultSet(ps.executeQuery());

        request.setAttribute("users", users);
      } else {
        forwardToDefault(request, response);
        return;
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }

    request.getRequestDispatcher("/add-user-service.jsp").forward(request, response);
  }

  private void doPostAdd(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Integer userId    = Utils.getIntParams(request, "user_id");
    Integer serviceId = Utils.getIntParams(request, "service_id");

    DataSource source = getDataSource(request);
    Connection conn = null;
    try {
      conn = source.getConnection();
      conn.setAutoCommit(false);
      PreparedStatement ps = conn.prepareStatement("insert into t_user_service_rela (service_id, user_id, version) values (?, ?, ?)");

      if (userId != null) {
        List<Integer> serviceToAdd = getIdToAdd(request, "service_");
        for (Integer id : serviceToAdd) {
          ps.setInt(1, id);
          ps.setInt(2, userId);
          ps.setInt(3, 0);

          ps.addBatch();
        }
      } else if (serviceId != null) {
        List<Integer> userToAdd = getIdToAdd(request, "user_");
        for (Integer id : userToAdd) {
          ps.setInt(1, serviceId);
          ps.setInt(2, id);
          ps.setInt(3, 0);

          ps.addBatch();
        }
      } else {
        forwardToDefault(request, response);
        return;
      }

      ps.executeBatch();
      conn.commit();
    } catch (SQLException e) {
      try {
        conn.rollback();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
      e.printStackTrace();
    } finally {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    String redirTo = getRedirTo(userId, serviceId);
    if (redirTo != null) {
      response.sendRedirect(redirTo);
    }
  }

  private void doGetDel(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    Integer userId    = Utils.getIntParams(request, "user_id");
    Integer serviceId = Utils.getIntParams(request, "service_id");

    Integer relaId = Utils.getIntParams(request, "rela_id");

    DataSource source = getDataSource(request);
    Connection conn = null;
    try {
      conn = source.getConnection();
      conn.setAutoCommit(false);

      PreparedStatement ps = conn.prepareStatement("delete from t_user_service_rela where rela_id = ?");
      ps.setInt(1, relaId);
      ps.executeUpdate();

      conn.commit();
    } catch (SQLException e) {
      try {
        conn.rollback();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    } finally {
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    String redirTo = getRedirTo(userId, serviceId);
    if (redirTo != null) {
      response.sendRedirect(redirTo);
    }
  }

  private String getRedirTo(Integer userId, Integer serviceId) {
    String redirTo = null;
    if (userId != null) {
      redirTo = "/subscribe/subscribe/user-service?oper=list&user_id=" + userId;
    } else if (serviceId != null) {
      redirTo = "/subscribe/subscribe/user-service?oper=list&service_id=" + serviceId;
    } else {
      redirTo = "/subscribe/subscribe/default";
    }
    return redirTo;
  }

  private List<Integer> getIdToAdd(HttpServletRequest request, String prefix) {
    List<Integer> toAdd = new ArrayList<>();
    final Enumeration<String> parameterNames = request.getParameterNames();
    while (parameterNames.hasMoreElements()) {
      String paramName = parameterNames.nextElement();
      if (paramName.startsWith(prefix)) {
        Integer curId = Utils.getInt(paramName.substring(prefix.length()));
        if (curId != null) {
          toAdd.add(curId);
        }
      }
    }
    return toAdd;
  }
}
