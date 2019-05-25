package com.vmvare.interview.handler;

import com.vmvare.interview.bean.User;
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

public class UserHandler extends DefaultHandler {

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
    DataSource dataSource = getDataSource(request);
    if (dataSource != null) {
      int pageStart = getPageStart(request);
      int pageSize  = getPageSize();
      try
          (Connection connection = dataSource.getConnection()) {
        PreparedStatement ps = connection.prepareStatement("select user_id, name, version from t_users order by user_id desc limit ?, ?");
        ps.setInt(1, pageStart);
        ps.setInt(2, pageSize);

        List<User> users = new ArrayList<>();
        ResultSet set = ps.executeQuery();
        while (set.next()) {
          User user = new User();
          user.setUserId(set.getInt(1));
          user.setName(set.getString(2));
          user.setVersion(set.getInt(3));

          users.add(user);
        }

        request.setAttribute("users", users);

        request.getRequestDispatcher("/user.jsp").forward(request, response);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }
}
