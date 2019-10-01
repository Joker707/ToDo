package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.Task;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ToDoListController {

    @FXML
    private Button refreshButton;

    @FXML
    private Button buttonDelete;

    @FXML
    private TextField textDelete;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ListView<Task> tasksList;

    @FXML
    private Button logOut;





    @FXML
    private Button addTask;

    private ObservableList<Task> tasks;

    DatabaseHandler databaseHandler;

    @FXML
    void initialize() throws SQLException {
        addTask.setOnAction(event -> {

            addTask.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/addItemForm.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });

        tasks = FXCollections.observableArrayList();

        databaseHandler = new DatabaseHandler();
        ResultSet taskSet = databaseHandler.getTasksByUserId(LoginController.userID);

        while (taskSet.next()) {
            Task task = new Task();

            task.setTaskId(taskSet.getInt("taskid"));
            task.setTask(taskSet.getString("task"));
            task.setDatecreated(taskSet.getTimestamp("datecreated"));
            task.setDescription(taskSet.getString("description"));
            tasks.addAll(task);


            tasksList.setItems(tasks);
            tasksList.setCellFactory(CellController -> new CellController());
        }


        logOut.setOnAction(event -> {
            logOut.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/login.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });


        buttonDelete.setOnAction(event -> {
            databaseHandler = new DatabaseHandler();

            int taskID = Integer.parseInt(textDelete.getText().trim());
            int userID = LoginController.userID;

            databaseHandler.deleteTask(userID, taskID);
        });

        refreshButton.setOnAction(event -> {
            refreshButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/toDoList.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
    }
}
