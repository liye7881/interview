package com.vmvare.interview.dao;

import com.vmvare.interview.bean.Service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDao extends AbstractDao {
  public static final String SERVICE_NOT_BELONG_TO_USER_SQL = "select a.service_id, a.name, a.version from t_services a "
      + "where not exists (select * from t_user_service_rela b where a.service_id = b.service_id and user_id = ?)"
      + " order by a.service_id desc";


  public List<Service> getServices() {
    List<Service> services = new ArrayList<>();
    try (Connection conn = getConnection()) {
      PreparedStatement ps = conn.prepareStatement("select a.service_id, a.name, a.version "
          + "from t_services a "
          + "order by a.service_id desc");

      ResultSet set = ps.executeQuery();
      services.addAll(getServicesForResultSet(set));
    } catch (SQLException e) {
      handleSQLException(e);
    }

    return services;
  }

  public List<Service> getServiceNotBelongToUser(int userId) {
    List<Service> services = new ArrayList<>();

    try (Connection conn = getConnection()) {
      PreparedStatement ps = conn.prepareStatement(SERVICE_NOT_BELONG_TO_USER_SQL);
      ps.setInt(1, userId);

      services.addAll(getServicesForResultSet(ps.executeQuery()));
    } catch (SQLException e) {
      handleSQLException(e);
    }

    return services;
  }

  private List<Service> getServicesForResultSet(ResultSet set) throws SQLException {
    List<Service> services = new ArrayList<>();

    while (set.next()) {
      Service service = new Service();
      service.setServiceId(set.getInt(1));
      service.setName(set.getString(2));
      service.setVersion(set.getInt(3));

      services.add(service);
    }

    return services;
  }

}
