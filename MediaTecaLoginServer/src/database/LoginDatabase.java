package database;

import messages.LoginMessage;

import java.sql.*;

public class LoginDatabase {

    private Connection connection;

    public LoginDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException c) {
            System.out.println("Error: Could not find MySQL Driver");
            System.exit(0);
        }

        try {
            String connectionString = "jdbc:mysql://localhost:3306/mediateca_database";
            this.connection = DriverManager.getConnection(connectionString,"login_server","Napoleon113");
        } catch (SQLException s) {
            System.out.println("Error: Could not connect to Login Database");
            System.exit(0);
        }
    }

    public synchronized String loginServer(String email,String password,String ipAddress) {
        try {
            Statement statement = this.connection.createStatement();
            String query = "SELECT username, ip_address FROM users join servers " +
                    "WHERE email = \"" + email + "\" and " + "password = \"" + password + "\"";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                String queryIpAddress = resultSet.getString("ip_address");
                String username = resultSet.getString("username");
                if (!ipAddress.equals(queryIpAddress)) {
                    updateIpAddress(email, ipAddress);
                }
                return username;
            }
            return null;
        }
        catch (SQLException s) {
            System.out.println("Error: LoginDatabase : loginServer");
            return null;
        }
    }

    public String loginUser(String email,String password) {
        try {
            Statement statement = this.connection.createStatement();
            String query = "SELECT username FROM users join servers " +
                    "WHERE email = \"" + email + "\" and " + "password = \"" + password + "\"";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getString("username");
            }
            return null;
        }
        catch (SQLException s) {
            System.out.println("Error: LoginDatabase : loginUser");
            return null;
        }
    }

    public synchronized String getServerIp(String passCode) {
        try {
            Statement statement = this.connection.createStatement();
            String query = "SELECT ip_address FROM servers " +
                    "WHERE passCode = \"" + passCode + "\"";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getString("ip_address");
            }
            return null;
        } catch (SQLException s) {
            System.out.println("Error: LoginDatabase : getServerIp");
            return null;
        }
    }

    private void updateIpAddress(String email,String ipAddress) {
        try {
            Statement statement = this.connection.createStatement();
            String query = "UPDATE servers " +
                    "SET ip_address = \"" + ipAddress + "\" " +
                    "WHERE email = \"" + email + "\"";
            statement.executeUpdate(query);
        }
        catch (SQLException e) {
            System.out.println("Error: LoginDatabase : updateIpAddress");
            //do something
        }
    }


}
