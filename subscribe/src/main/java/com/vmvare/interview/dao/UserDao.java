package com.vmvare.interview.dao;

import com.vmvare.interview.bean.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDao extends AbstractDao {
  public static final String USER_NOT_BELONG_SERVICE_SQL = "select a.user_id, a.name, a.version from t_users a "
      + "where not exists (select * from t_user_service_rela b where a.user_id = b.user_id and b.service_id = ?) "
      + "order by a.user_id";

  public List<User> getUsers() {
    List<User> users = new ArrayList<>();

    try (Connection conn = getConnection()){
      PreparedStatement ps = conn.prepareStatement("select user_id, name, version from t_users order by user_id desc");
      ResultSet set = ps.executeQuery();
      users.addAll(getUsersFromResultSet(set));
    } catch (SQLException e) {
      handleSQLException(e);
    }

    return users;
  }


  public List<User> getUserNotBelongToService(int serviceId) {
    List<User> users = new ArrayList<>();

    try (Connection conn = getConnection()) {
      PreparedStatement ps = conn.prepareStatement(USER_NOT_BELONG_SERVICE_SQL);
      ps.setInt(1, serviceId);

      users.addAll(getUsersFromResultSet(ps.executeQuery()));
    } catch (SQLException e) {
      handleSQLException(e);
    }

    return users;
  }

  private List<User> getUsersFromResultSet(ResultSet set) throws SQLException {
    List<User> users = new ArrayList<>();
    while (set.next()) {
      User user = new User();
      user.setUserId(set.getInt(1));
      user.setName(set.getString(2));
      user.setVersion(set.getInt(3));

      users.add(user);
    }

    return users;
  }
}
