package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import sample.database.DatabaseHandler;
import sample.model.Task;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CellController extends ListCell<Task> {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private Label cellDate;

    @FXML
    private Label cellDescription;

    @FXML
    private Label cellTask;

    @FXML
    private Button cellDelete;

    FXMLLoader fxmlLoader;

    DatabaseHandler databaseHandler;

    @FXML
    void initialize() {
        if (fxmlLoader == null) {
            fxmlLoader = new FXMLLoader(getClass().getResource("/sample/cell.fxml"));
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void updateItem(Task item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            initialize();

            cellDate.setText(item.getDatecreated().toString());
            cellDescription.setText(item.getDescription());
            cellTask.setText(item.getTask());

            cellDelete.setOnAction(event -> {
                databaseHandler = new DatabaseHandler();

                databaseHandler.deleteTask(LoginController.userID, item.getTaskId());

            });

            setText(null);
            setGraphic(rootAnchorPane);
        }
    }
}
