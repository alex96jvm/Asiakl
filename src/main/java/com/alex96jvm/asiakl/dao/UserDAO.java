package com.alex96jvm.asiakl.dao;
import com.alex96jvm.asiakl.config.DatabaseConfig;
import javax.sql.DataSource;
import java.sql.*;

public class UserDAO {
    public int authenticate(String username, String password) throws SQLException {
        DataSource dataSource = DatabaseConfig.getDataSource();
        String sql = "SELECT * FROM players WHERE username = ? AND password = ?";
        try (Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id");
                } else {
                    return -1;
                }
            }
        }
    }
}