package com.vmvare.interview;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

  public static Class[] getTargetInterfaces(Object target) {
    List<Class> classes = new ArrayList<>();
    Class<?> clz = target.getClass();
    while (clz != null) {
      Arrays.stream(clz.getInterfaces()).forEach(classes::add);
      clz = clz.getSuperclass();
    }

    return classes.toArray(new Class[0]);
  }
}
