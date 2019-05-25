package com.vmvare.interview.dao.dialect;

import com.vmvare.interview.SubcribeProp;
import com.vmvare.interview.SubscribeException;
import java.util.Properties;

public class DialectFactory {
  private static Properties properties = SubcribeProp.getINSTANCE().getProperties();
  public static Dialect getDialect() {
    Dialect dialect;
    String driverClassName = properties.getProperty("driverClassName");
    switch (driverClassName) {
      case "org.hsqldb.jdbc.JDBCDriver":
        dialect = new HSQLDialect();
        break;
      default:
        throw new SubscribeException("Database for " + driverClassName + " is not support");
    }
    return dialect;
  }
}
