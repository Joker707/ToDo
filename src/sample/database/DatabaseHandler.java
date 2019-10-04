package sample.database;

import sample.model.Task;
import sample.model.User;

import java.sql.*;

public class DatabaseHandler extends Configs {

//    Configs config = null;
    Connection dbConnection;


    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort.toString() + "/" + dbName + "?serverTimezone=UTC";

//        String.format(null);

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPassword);

        return dbConnection;
    }

    public void signUpUser(User user) {
        String insert = "INSERT INTO " + Const.USERS.TABLE + "(" + Const.USERS.FIRSTNAME + "," + Const.USERS.LASTNAME +
                "," + Const.USERS.USERNAME + "," + Const.USERS.PASSWORD + ")" + "VALUES(?,?,?,?)";

            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getUserName());
                preparedStatement.setString(4, user.getPassword());

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public ResultSet getUser(User user) {

        ResultSet resultSet = null;

        if (!user.getUserName().equals("") && !user.getPassword().equals("")) {
            String query = "SELECT * FROM " + Const.USERS.TABLE + " WHERE " + Const.USERS.USERNAME +
                    "=?" + " AND " + Const.USERS.PASSWORD + "=?";

            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

                preparedStatement.setString(1, user.getUserName());
                preparedStatement.setString(2, user.getPassword());

                resultSet = preparedStatement.executeQuery();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return resultSet;
    }


    public ResultSet getUsername(User user) {

        ResultSet resultSet = null;

        if (!user.getUserName().equals("") && !user.getPassword().equals("")) {
            String query = "SELECT * FROM " + Const.USERS.TABLE + " WHERE " + Const.USERS.USERNAME +
                    "=?";

            try {
                PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);

                preparedStatement.setString(1, user.getUserName());

                resultSet = preparedStatement.executeQuery();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        return resultSet;
    }


    public void addTask(Task task) {
        String insert = "INSERT INTO " + Const.TASKS.TABLE + "(" + Const.TASKS.USERID + "," + Const.TASKS.DATE +
                "," + Const.TASKS.DESCRIPTION + "," + Const.TASKS.TASK + ")" + "Values(?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);

            preparedStatement.setInt(1, task.getUserId());
            preparedStatement.setTimestamp(2, task.getDatecreated());
            preparedStatement.setString(3, task.getDescription());
            preparedStatement.setString(4, task.getTask());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getTasksByUserId(int userId) {

        ResultSet resultTasks = null;

        String query = "SELECT * FROM " + Const.TASKS.TABLE + " WHERE " + Const.TASKS.USERID + "=?";
        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(query);
            preparedStatement.setInt(1, userId);

            resultTasks = preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultTasks;
    }

    public void deleteTask(int userId, int taskId) {
        String query = "DELETE FROM " + Const.TASKS.TABLE + " WHERE " + Const.TASKS.USERID + "=?" + " AND " + Const.TASKS.ID + "=?";

        PreparedStatement preparedStatement;
        try {
            preparedStatement = getDbConnection().prepareStatement(query);

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, taskId);

            preparedStatement.execute();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
