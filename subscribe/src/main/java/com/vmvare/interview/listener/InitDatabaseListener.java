package com.vmvare.interview.listener;

import com.vmvare.interview.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.commons.dbcp.BasicDataSource;

public class InitDatabaseListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    Properties properties = new Properties();
    try {
      properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties"));
      System.getProperties().forEach((key, value) -> {
        properties.setProperty((String) key, (String) value);
      });
    } catch (IOException e) {
    }

    BasicDataSource source = new BasicDataSource();
    source.setDriverClassName(properties.getProperty("driverClassName"));
    source.setUrl(properties.getProperty("url"));
    source.setUsername(properties.getProperty("username"));
    source.setPassword(properties.getProperty("password"));

    sce.getServletContext().setAttribute(Constants.DATASOURCE, source);

    boolean init = true;
    try (Connection conn = source.getConnection()) {
      conn.prepareStatement("select count(*) from t_services1");
      init = false;
    } catch (SQLException e) {
    }

    if (init) {
      try
          (Connection conn = source.getConnection();
              BufferedReader reader = new BufferedReader(new InputStreamReader(
                  Thread.currentThread().getContextClassLoader()
                      .getResourceAsStream("database.sql")))) {
        String line;
        while ((line = reader.readLine()) != null) {
          PreparedStatement ps = conn.prepareStatement(line);
          ps.executeUpdate();
        }

        ResultSet set = conn.prepareStatement("select count(*) from t_services").executeQuery();
        while (set.next()) {
          System.out.println("has services " + set.getInt(1));
        }

        set = conn.prepareStatement("select count(*) from t_users").executeQuery();
        while (set.next()) {
          System.out.println("has users " + set.getInt(1));
        }
      } catch (SQLException | IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    sce.getServletContext().removeAttribute(Constants.DATASOURCE);
  }
}
