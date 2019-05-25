package com.vmvare.interview.dao;

import com.vmvare.interview.Utils;
import com.vmvare.interview.dao.dialect.Dialect;
import com.vmvare.interview.dao.dialect.DialectFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AutoPagingConnection implements InvocationHandler {
  private static Dialect dialect = DialectFactory.getDialect();

  private Connection conn;

  public AutoPagingConnection(Connection conn) {
    this.conn = conn;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    Object ret;
    if (matchedMethod(method, args)) {
      Class<PreparedStatement> psClz = PreparedStatement.class;
      args[0] = dialect.getDialectPagingSQL((String) args[0]);

      ret = method.invoke(conn, args);
      ret = Proxy.newProxyInstance(
          psClz.getClassLoader(), Utils.getTargetInterfaces(ret), new AutoPagingPreparedStatement(
              (PreparedStatement) ret));
    } else {
      ret = method.invoke(conn, args);
    }

    return ret;
  }

  protected boolean matchedMethod(Method method, Object[] args) {
    return method.getName().equals("prepareStatement") &&
        args.length == 1 && args[0].getClass().getName().equals(String.class.getName());
  }
}
