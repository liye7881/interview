package com.vmvare.interview.dao;

import com.vmvare.interview.bean.Service;
import com.vmvare.interview.bean.User;
import com.vmvare.interview.bean.UserService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class UserServiceDao extends AbstractDao {

  private static final String USER_SQL = "select c.name, a.name, b.rela_id "
      + "from t_services a, t_user_service_rela b, t_users c "
      + "where a.service_id = b.service_id and b.user_id = c.user_id and b.user_id = ? order by a.service_id desc";
  private static final String SERVICE_SQL = "select a.name, c.name, b.rela_id "
      + "from t_users a, t_user_service_rela b, t_services c "
      + "where a.user_id = b.user_id and b.service_id = c.service_id and b.service_id = ? order by c.service_id desc";

  public List<UserService> getUserServiceBelongToUser(int userId) {
    return getUserService(USER_SQL, userId);
  }

  public List<UserService> getUserServiceBelongToService(int userId) {
    return getUserService(SERVICE_SQL, userId);
  }

  public void addUserServiceUnderUser(List<Integer> serviceIds, int userId) {
    List<Integer> userIds = new ArrayList<>();
    IntStream.range(0, serviceIds.size()).forEach(i -> userIds.add(userId));

    addUserService(serviceIds, userIds);
  }

  public void addUserServiceUnderService(int serviceId, List<Integer> userIds) {
    List<Integer> serviceIds = new ArrayList<>();
    IntStream.range(0, userIds.size()).forEach(i -> serviceIds.add(serviceId));

    addUserService(serviceIds, userIds);
  }

  public void deleteUserService(int relaId) {
    Connection conn = null;
    try {
      conn = getConnection();
      conn.setAutoCommit(false);

      PreparedStatement ps = conn.prepareStatement("delete from t_user_service_rela where rela_id = ?");
      ps.setInt(1, relaId);
      ps.executeUpdate();

      conn.commit();
    } catch (SQLException e) {
      try {
        conn.rollback();
      } catch (SQLException ex) {
        handleSQLException(ex);
      }
      handleSQLException(e);
    } finally {
      try {
        conn.close();
      } catch (SQLException e) {
        handleSQLException(e);
      }
    }
  }

  private void addUserService(List<Integer> serviceIds, List<Integer> userIds) {
    Connection conn = null;
    try {
      conn = getConnection();
      PreparedStatement ps = conn.prepareStatement("insert into t_user_service_rela (service_id, user_id, version) values (?, ?, ?)");
      for (int i = 0; i < serviceIds.size(); i++) {
        ps.setInt(1, serviceIds.get(i));
        ps.setInt(2, userIds.get(i));
        ps.setInt(3, 0);

        ps.addBatch();
      }

      ps.executeBatch();
      conn.commit();
    } catch (SQLException e) {
      try {
        conn.rollback();
      } catch (SQLException ex) {
        handleSQLException(e);
      }
      handleSQLException(e);
    } finally {
      try {
        conn.close();
      } catch (SQLException e) {
        handleSQLException(e);
      }
    }
  }

  private List<UserService> getUserService(String sql, int id) {
    List<UserService> userServices = new ArrayList<>();

    try (Connection conn = getConnection()) {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setInt(1, id);

      ResultSet set = ps.executeQuery();

      while (set.next()) {
        User user = new User();
        user.setName(set.getString(1));

        Service service = new Service();
        service.setName(set.getString(2));

        UserService userService = new UserService();
        userService.setUser(user);
        userService.setService(service);
        userService.setRelaId(set.getInt(3));

        userServices.add(userService);
      }
    } catch (SQLException e) {
      handleSQLException(e);
    }

    return userServices;
  }
}
