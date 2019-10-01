package sample.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField signUpFirstName;

    @FXML
    private TextField signUpLastName;

    @FXML
    private Button signUpBack;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField signUpUsername;

    @FXML
    void initialize() {
        createUser();

    }

    private void createUser() {
        DatabaseHandler databaseHandler = new DatabaseHandler();


        signUpButton.setOnAction(event -> {
            String signUpFirst = signUpFirstName.getText().trim();
            String signUpLast = signUpLastName.getText().trim();
            String signUpUser = signUpUsername.getText().trim();
            String signUpPass = signUpPassword.getText().trim();

            User user = new User(signUpFirst, signUpLast, signUpUser, signUpPass);

            databaseHandler.signUpUser(user);
        });

        signUpBack.setOnAction(event -> {
            signUpBack.getScene().getWindow().hide();
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
    }
}
