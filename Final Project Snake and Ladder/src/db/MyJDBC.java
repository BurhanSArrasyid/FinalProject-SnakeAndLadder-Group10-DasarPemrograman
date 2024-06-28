package db;

import constants.CommonConstants;

import java.sql.*;

//JDBC - Java Database Connectivity
public class MyJDBC {
    // true - register success
    public static boolean register(String username,String password) {
        // check existing username
        try {
            if (!checkUser(username)) {
                Connection connection = DriverManager.getConnection(CommonConstants.DB_URL,
                        CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);

                // create insert query
                PreparedStatement insertUser = connection.prepareStatement(
                        "INSERT INTO " + CommonConstants.DB_USERS_TABLE_NAME + "(username, password)" +
                                "VALUES(?, ?)"
                );

                // insert parameters in the query
                insertUser.setString(1, username);
                insertUser.setString(2, password);

                // update db with new user
                insertUser.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // true - user exists in database
    public static boolean checkUser(String username) {
        try {
            Connection connection = DriverManager.getConnection(CommonConstants.DB_URL,
                    CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);

            PreparedStatement checkUserExists = connection.prepareStatement(
                    "SELECT * FROM " + CommonConstants.DB_USERS_TABLE_NAME +
                            " WHERE USERNAME = ?"
            );
            checkUserExists.setString(1, username);

            ResultSet resultSet = checkUserExists.executeQuery();

            // check if the result set is empty, so if empty, user doesn't exist
            if (!resultSet.isBeforeFirst()) {
                return false;
            }

        } catch(SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    // validate login credentials to see if username and password pair is exist in database
    public static boolean validateLogin(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection(CommonConstants.DB_URL,
                    CommonConstants.DB_USERNAME, CommonConstants.DB_PASSWORD);

            // create select query
            PreparedStatement validateUser = connection.prepareStatement(
                    "SELECT * FROM " + CommonConstants.DB_USERS_TABLE_NAME +
                            " WHERE USERNAME = ? AND PASSWORD = ?"
            );
            validateUser.setString(1, username);
            validateUser.setString(2, password);

            ResultSet resultSet = validateUser.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}
