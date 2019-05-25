package com.vmvare.interview.handler;

import com.vmvare.interview.bean.Service;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class ServiceHandler extends DefaultHandler {

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
    int pageStart = getPageStart(request);
    int pageSize  = getPageSize();

    DataSource source = getDataSource(request);
    try (Connection conn = source.getConnection()) {
      PreparedStatement ps = conn.prepareStatement("select a.service_id, a.name, a.version "
          + "from t_services a "
          + "order by a.service_id desc limit ?, ?");
      ps.setInt(1, pageStart);
      ps.setInt(2, pageSize);

      List<Service> services = getServicesForResultSet(ps.executeQuery());

      request.setAttribute("services", services);

      request.getRequestDispatcher("/service.jsp").forward(request, response);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
