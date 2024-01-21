package io.hexlet;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Application {
    public static void main(String[] args) throws SQLException {
        try(var conn = DriverManager.getConnection("jdbc:h2:mem:test")){
            var sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, username VARCHAR(255), phone VARCHAR(255))";
            try(var statement = conn.createStatement()) {
                statement.execute(sql);
            }


            //var sql2 = "INSERT INTO users (username, phone) VALUES ('tommy', '123456789')";
            var sql2 = "INSERT INTO users (username, phone) VALUES (?, ?)";
            try(var preparedStatement = conn.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, "Tommy");
                preparedStatement.setString(2, "123456789");
                preparedStatement.executeUpdate();

                preparedStatement.setString(1, "Jacky");
                preparedStatement.setString(2, "33333333");
                preparedStatement.executeUpdate();

                var sql4 = "DELETE FROM users WHERE username = ?";
                try(var statement4 = conn.prepareStatement(sql4)) {
                    statement4.setString(1, "Jacky");
                    statement4.executeUpdate();
                }
            }

            var sql3 = "SELECT * FROM users";
            try(var statement3 = conn.createStatement()) {
                var resultSet = statement3.executeQuery(sql3);
                while (resultSet.next()) {
                    System.out.println(resultSet.getString("username"));
                    System.out.println(resultSet.getString("phone"));
                }
            }
        }
    }
}