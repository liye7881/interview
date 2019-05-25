package com.vmvare.interview.handler;

import com.vmvare.interview.dao.ServiceDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServiceHandler extends DefaultHandler {

  private ServiceDao serviceDao = new ServiceDao();

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
    request.setAttribute("services", serviceDao.getServices());
    request.getRequestDispatcher("/service.jsp").forward(request, response);
  }
}
