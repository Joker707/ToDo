package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.patterns.SingleTonShowMenu;
import sample.animations.Shaker;
import sample.database.DatabaseHandler;
import sample.model.User;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class LoginController {

    public static int userID;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginUsername;

    @FXML
    private Button loginButton;

    @FXML
    private Button loginSignUpButton;

    @FXML
    private PasswordField loginPassword;

    private DatabaseHandler databaseHandler;

    SingleTonShowMenu singleTonShowMenu = SingleTonShowMenu.getInstance();

    @FXML
    void initialize() {

        databaseHandler = new DatabaseHandler();


        loginButton.setOnAction(event -> {

            String loginUser = loginUsername.getText().trim();
            String loginPass = loginPassword.getText().trim();

            if (!loginUser.equals("") && !loginPass.equals("")) {


                User user = new User();
                user.setUserName(loginUser);
                user.setPassword(loginPass);

                ResultSet userSet = databaseHandler.getUser(user);


                int count = 0;

                try {

                    while (userSet.next()) {
                        count++;
                        userID = userSet.getInt("userid");
                    }

                    if (count >= 1) {
                        SingleTonShowMenu singleTonShowMenu = SingleTonShowMenu.getInstance();
                        singleTonShowMenu.setLocationAndCloseStage(loginButton, "toDoList");
                    } else {
                        Shaker shakerUser = new Shaker(loginUsername);
                        Shaker shakerPass = new Shaker(loginPassword);
                        shakerUser.shake();
                        shakerPass.shake();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                singleTonShowMenu.showErrorAlert("Fill in the fields");
            }
        });


        loginSignUpButton.setOnAction(event ->
            singleTonShowMenu.setLocationAndCloseStage(loginSignUpButton, "signUp"));
    }

}
