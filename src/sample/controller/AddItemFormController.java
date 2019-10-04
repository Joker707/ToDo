package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.patterns.SingleTonShowMenu;
import sample.database.DatabaseHandler;
import sample.model.Task;

import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AddItemFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button goBackButton;

    @FXML
    private TextField addItemFormDescription;

    @FXML
    private Button addItemFormButton;

    @FXML
    private TextField addItemFormTask;

    SingleTonShowMenu singleTonShowMenu = SingleTonShowMenu.getInstance();

    @FXML
    void initialize() {
        addTaskAndDescription();

    }

    private void addTaskAndDescription() {
        DatabaseHandler databaseHandler = new DatabaseHandler();

        addItemFormButton.setOnAction(event -> {

            String addTask = addItemFormTask.getText().trim();
            String addDescription = addItemFormDescription.getText().trim();

            Task task = new Task();
            Calendar calendar = Calendar.getInstance();
            Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());

            if (!addTask.equals("") && !addDescription.equals("")) {

                task.setUserId(LoginController.userID);
                task.setTask(addTask);
                task.setDatecreated(timestamp);
                task.setDescription(addDescription);

                databaseHandler.addTask(task);
                singleTonShowMenu.showInformationAlert("Task successfully added!!");
            } else {
                singleTonShowMenu.showErrorAlert("Fill in the fields");
            }
        });

        goBackButton.setOnAction(event -> {
            singleTonShowMenu.setLocationAndCloseStage(goBackButton, "toDoList");
        });
    }
}
