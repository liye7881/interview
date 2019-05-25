package com.vmvare.interview.handler;

import com.vmvare.interview.Utils;
import com.vmvare.interview.bean.Service;
import com.vmvare.interview.bean.User;
import com.vmvare.interview.bean.UserService;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class UserServiceHandler extends DefaultHandler {

  private static final String USER_SQL = "select c.name, a.name, b.rela_id "
      + "from t_services a, t_user_service_rela b, t_users c "
      + "where a.service_id = b.service_id and b.user_id = c.user_id and b.user_id = ? order by a.service_id desc limit ?, ?";
  private static final String SERVICE_SQL = "select a.name, c.name, b.rela_id "
      + "from t_users a, t_user_service_rela b, t_services c "
      + "where a.user_id = b.user_id and b.service_id = c.service_id and b.service_id = ? order by c.service_id desc limit ?, ?";

  @Override
  public void handleGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String oper = getOper(request);
    switch (oper) {
      case "list":
        doList(request, response);
        break;
      default:
        forwardToDefault(request, response);
    }
  }

  private void doList(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Integer userId    = Utils.getIntParams(request, "user_id");
    Integer serviceId = Utils.getIntParams(request, "service_id");

    int pageStart = getPageStart(request);
    int pageSize  = getPageSize();

    DataSource source = getDataSource(request);
    try (Connection conn = source.getConnection()) {
      List<UserService> userServices = new ArrayList<>();

      PreparedStatement ps = null;
      if (userId != null) {
        ps = conn.prepareStatement(USER_SQL);
        ps.setInt(1, userId);
        ps.setInt(2, pageStart);
        ps.setInt(3, pageSize);
      } else if (serviceId != null) {
        ps = conn.prepareStatement(SERVICE_SQL);
        ps.setInt(1, serviceId);
        ps.setInt(2, pageStart);
        ps.setInt(3, pageSize);
      } else {
        forwardToDefault(request, response);
        return;
      }

      ResultSet set = ps.executeQuery();

      while (set.next()) {
        User user = new User();
        user.setName(set.getString(1));

        Service service = new Service();
        service.setName(set.getString(2));

        UserService userService = new UserService();
        userService.setUser(user);
        userService.setService(service);
        userService.setRelaId(set.getInt(3));

        userServices.add(userService);;
      }

      request.setAttribute("userServices", userServices);
      request.getRequestDispatcher("/user-service.jsp").forward(request, response);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
