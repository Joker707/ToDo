package sample.database;

import sample.model.Task;
import sample.model.User;

import java.sql.*;

public class DatabaseHandler extends Configs{

    Connection dbConnetcion;

    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectinString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName + "?serverTimezone=UTC";

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnetcion = DriverManager.getConnection(connectinString, dbUser, dbPassword);

        return dbConnetcion;
    }

    public void signUpUser(User user) {
        String insert = "INSERT INTO " + Const.USERS_TABLE + "(" + Const.USERS_FIRSTNAME + "," + Const.USERS_LASTNAME +
                "," + Const.USERS_USERNAME + "," + Const.USERS_PASSWORD + ")" + "VALUES(?,?,?,?)";

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
            String query = "SELECT * FROM " + Const.USERS_TABLE + " WHERE " + Const.USERS_USERNAME +
                    "=?" + " AND " + Const.USERS_PASSWORD + "=?";

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


        } else {
            System.out.println("Sosi");
        }
        return resultSet;
    }


    public void addTask(Task task) {
        String insert = "INSERT INTO " + Const.TASKS_TABLE + "(" + Const.TASKS_USERID + "," + Const.TASKS_DATE +
                "," + Const.TASKS_DESCRIPTION + "," + Const.TASKS_TASK + ")" + "Values(?,?,?,?)";

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

        String query = "SELECT * FROM " + Const.TASKS_TABLE + " WHERE " + Const.TASKS_USERID + "=?";
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
        String query = "DELETE FROM " + Const.TASKS_TABLE + " WHERE " + Const.TASKS_USERID + "=?" + " AND " + Const.TASKS_ID + "=?";

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
