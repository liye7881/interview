package com.vmvare.interview.dao.dialect;

public class HSQLDialect implements Dialect {

  @Override
  public String getDialectPagingSQL(String sql) {
    final String lowerCase = sql.toLowerCase();
    if (lowerCase.contains("select") && !lowerCase.contains("limit ")) {
      sql = sql + " limit ?, ?";
    }
    return sql;
  }
}
