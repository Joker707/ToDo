package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.Task;
import sample.patterns.SingleTonShowMenu;

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

    SingleTonShowMenu singleTonShowMenu = SingleTonShowMenu.getInstance();

    @FXML
    void initialize() {
        if (fxmlLoader == null) {
            fxmlLoader = new FXMLLoader(getClass().getResource("/sample/view/cell.fxml"));
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

                if (singleTonShowMenu.showConfirmationAlert("Delete task",
                        "Do you really want to delete it?").get() == ButtonType.OK) {
                    databaseHandler.deleteTask(LoginController.userID, item.getTaskId());
                    getListView().getItems().remove(getItem());
                }
            });

            setText(null);
            setGraphic(rootAnchorPane);
        }
    }
}
