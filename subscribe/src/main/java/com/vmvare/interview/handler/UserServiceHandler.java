package com.vmvare.interview.handler;

import com.vmvare.interview.Utils;
import com.vmvare.interview.bean.UserService;
import com.vmvare.interview.dao.UserServiceDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServiceHandler extends DefaultHandler {
  private UserServiceDao userServiceDao = new UserServiceDao();

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

    List<UserService> userServices = new ArrayList<>();

    if (userId != null) {
      userServices.addAll(userServiceDao.getUserServiceBelongToUser(userId, pageStart, pageSize));
    } else if (serviceId != null) {
      userServices.addAll(userServiceDao.getUserServiceBelongToService(serviceId, pageStart, pageSize));
    } else {
      forwardToDefault(request, response);
      return;
    }

    request.setAttribute("userServices", userServices);
    request.getRequestDispatcher("/user-service.jsp").forward(request, response);
  }
}
