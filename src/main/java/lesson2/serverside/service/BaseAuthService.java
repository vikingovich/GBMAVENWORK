package lesson2.serverside.service;


import lesson2.serverside.interfaces.AuthService;

import java.sql.*;

public class BaseAuthService implements AuthService {

    //  private List<Entry> entryList;
    static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB = "jdbc:mysql://localhost/newbase";
    static final String USER = "root";
    static final String PASSWORD = "root";

    public BaseAuthService() {

    }

    @Override
    public void start() {
        System.out.println("AuthService start");
    }

    @Override
    public void stop() {
        System.out.println("AuthService stop");
    }

    @Override
    public String getNickByLoginAndPassword(String login, String password) {
        String snick = null;
        String query = "SELECT * FROM users WHERE login = ? AND password = ?";
        PreparedStatement preparedStatement = null;
        try {
            Class.forName(DRIVER);
            try (Connection conn = DriverManager.getConnection(DB, USER, PASSWORD);) {

                preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    snick = resultSet.getString("nick");
                }

            }

            return snick;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    private class Entry {
        private String login;
        private String password;
        private String nick;

        public Entry(String login, String password, String nick) {
            this.login = login;
            this.password = password;
            this.nick = nick;
        }
    }
}
