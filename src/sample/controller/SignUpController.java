package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.patterns.SingleTonShowMenu;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    SingleTonShowMenu singleTonShowMenu = SingleTonShowMenu.getInstance();


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
            User testUser = new User();
            testUser.setUserName(signUpUser);
            testUser.setPassword(signUpPass);


            ResultSet signUpUserSet = databaseHandler.getUsername(testUser);

            int count = 0;

            try {
                while (signUpUserSet.next()) {
                    count++;
                }

                if (count == 0) {
                    databaseHandler.signUpUser(user);
                    singleTonShowMenu.showInformationAlert("Account successfully created!! " +
                            "Go back login page to start using this perfect application.");

                } else {
                    singleTonShowMenu.showErrorAlert("This account already exist");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        signUpBack.setOnAction(event ->
                singleTonShowMenu.setLocationAndCloseStage(signUpBack, "login"));
    }
}
