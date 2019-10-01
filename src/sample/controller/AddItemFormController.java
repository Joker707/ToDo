package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.Task;

import java.io.IOException;
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
            } else {
                System.out.println("....");
            }
        });

        goBackButton.setOnAction(event -> {
            goBackButton.getScene().getWindow().hide();
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
