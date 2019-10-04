package sample.patterns;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class SingleTonShowMenu {

    private static SingleTonShowMenu instance = new SingleTonShowMenu();

    private SingleTonShowMenu() {
    }

    public static SingleTonShowMenu getInstance() {
        return instance;
    }

    public void setLocationAndCloseStage(Button button, String string) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(instance.getClass().getResource("/sample/view/" + string + ".fxml"));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        Stage oldStage = (Stage) button.getScene().getWindow();
        oldStage.close();

    }

    public void showErrorAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Error alert");
        alert.setHeaderText(null);
        alert.setContentText(contentText);

        alert.showAndWait();
    }


    public void showInformationAlert(String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Information alert");
        alert.setHeaderText(null);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    public Optional<ButtonType> showConfirmationAlert(String title, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(contentText);
        alert.setContentText(null);

        Optional<ButtonType> option = alert.showAndWait();
        return option;
    }
}
