package com.vmvare.interview.dao;

import com.vmvare.interview.SubcribeContext;
import com.vmvare.interview.handler.Handler;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;

public class AutoPagingPreparedStatement implements InvocationHandler {
  private PreparedStatement ps;
  private int index = 1;

  public AutoPagingPreparedStatement(PreparedStatement ps) {
    this.ps = ps;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    if (method.getName().startsWith("set") && args.length >= 2) {
      index = (int) args[0] + 1;
    } else if (method.getName().equals("executeQuery") && (args == null || args.length == 0 )) {
      SubcribeContext context = SubcribeContext.getContext();
      Handler handler = context.getHandler();
      ps.setInt(index++, handler.getPageStart(context.getRequest()));
      ps.setInt(index++, handler.getPageSize());
    }

    return method.invoke(ps, args);
  }
}
