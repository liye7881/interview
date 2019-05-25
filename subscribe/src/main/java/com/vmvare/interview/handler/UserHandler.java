package com.vmvare.interview.handler;

import com.vmvare.interview.dao.UserDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserHandler extends DefaultHandler {
  private UserDao userDao = new UserDao();

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
    request.setAttribute("users", userDao.getUsers());

    request.getRequestDispatcher("/user.jsp").forward(request, response);
  }
}
