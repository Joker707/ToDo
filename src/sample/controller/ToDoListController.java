package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import sample.patterns.SingleTonShowMenu;
import sample.database.DatabaseHandler;
import sample.model.Task;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ToDoListController {


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

    private DatabaseHandler databaseHandler;

    SingleTonShowMenu singleTonShowMenu = SingleTonShowMenu.getInstance();

    @FXML
    void initialize() throws SQLException {
        addTask.setOnAction(event -> {
            SingleTonShowMenu singleTonShowMenu = SingleTonShowMenu.getInstance();
            singleTonShowMenu.setLocationAndCloseStage(addTask, "addItemForm");
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

            tasksList.setCellFactory(CellController -> new CellController());
            tasksList.setItems(tasks);
        }


        logOut.setOnAction(event -> {
            if (singleTonShowMenu.showConfirmationAlert("Log out",
                    "Do you really want to log out?").get() == ButtonType.OK) {
                singleTonShowMenu.setLocationAndCloseStage(logOut, "login");
            }
        });
    }
}
