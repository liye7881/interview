package com.vmvare.interview;

import java.io.IOException;
import java.util.Properties;

public class SubcribeProp {
  private static SubcribeProp INSTANCE = new SubcribeProp();
  private Properties properties = new Properties();

  private SubcribeProp() {
    try {
      properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("database.properties"));
      System.getProperties().forEach((key, value) -> {
        properties.setProperty((String) key, (String) value);
      });
    } catch (IOException e) {
    }
  }

  public static SubcribeProp getINSTANCE() {
    return INSTANCE;
  }

  public Properties getProperties() {
    return properties;
  }
}
