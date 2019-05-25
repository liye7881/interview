package com.vmvare.interview.handler;

import com.vmvare.interview.Utils;
import com.vmvare.interview.bean.Service;
import com.vmvare.interview.bean.User;
import com.vmvare.interview.dao.ServiceDao;
import com.vmvare.interview.dao.UserDao;
import com.vmvare.interview.dao.UserServiceDao;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OperUserServiceHandler extends DefaultHandler {
  private UserDao userDao = new UserDao();

  private ServiceDao serviceDao = new ServiceDao();

  private UserServiceDao userServiceDao = new UserServiceDao();

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

    if (userId != null) {
      List<Service> services = serviceDao.getServiceNotBelongToUser(userId, pageStart, pageSize);

      request.setAttribute("services", services);
    } else if (serviceId != null){
      List<User> users = userDao.getUserNotBelongToService(serviceId, pageStart, pageSize);
      request.setAttribute("users", users);
    } else {
      forwardToDefault(request, response);
      return;
    }

    request.getRequestDispatcher("/add-user-service.jsp").forward(request, response);
  }

  private void doPostAdd(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    Integer userId    = Utils.getIntParams(request, "user_id");
    Integer serviceId = Utils.getIntParams(request, "service_id");

    if (userId != null) {
      List<Integer> serviceToAdd = getIdToAdd(request, "service_");
      userServiceDao.addUserServiceUnderUser(serviceToAdd, userId);
    } else if (serviceId != null) {
      List<Integer> userToAdd = getIdToAdd(request, "user_");
      userServiceDao.addUserServiceUnderService(serviceId, userToAdd);
    } else {
      forwardToDefault(request, response);
      return;
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
    userServiceDao.deleteUserService(relaId);

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
