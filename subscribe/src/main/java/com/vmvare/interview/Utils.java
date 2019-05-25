package com.vmvare.interview;

import javax.servlet.http.HttpServletRequest;

public class Utils {
  public static Integer getIntParams(HttpServletRequest request, String name) {
    return getInt(request.getParameter(name));
  }

  public static Integer getInt(String s) {
    Integer ret = null;
    if (s != null) {
      try {
        ret = Integer.valueOf(s);
      } catch (NumberFormatException e) {
      }
    }
    return ret;
  }
}
