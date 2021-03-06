package com.vmvare.interview.dao;

import com.vmvare.interview.Constants;
import com.vmvare.interview.SubcribeContext;
import com.vmvare.interview.SubscribeException;
import com.vmvare.interview.Utils;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

public class AbstractDao {

  protected void handleSQLException(SQLException e) {
    e.printStackTrace();
    throw new SubscribeException(e);
  }

  protected Connection getConnection(boolean autoCommit) throws SQLException {
    DataSource datasource = (DataSource) SubcribeContext.getContext().getRequest().getServletContext()
        .getAttribute(Constants.DATASOURCE);

    Connection conn = datasource.getConnection();
    conn.setAutoCommit(autoCommit);

    final Class<Connection> connClz = Connection.class;
    Connection proxyConn = (Connection) Proxy.newProxyInstance(connClz.getClassLoader(),
        Utils.getTargetInterfaces(conn), new AutoPagingConnection(conn));

    return proxyConn;
  }

  protected Connection getConnection() throws SQLException {
    return getConnection(false);
  }
}
