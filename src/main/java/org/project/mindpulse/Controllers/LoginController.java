package org.project.mindpulse.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.project.mindpulse.SystemManagement.UserHandler;


import java.io.IOException;

public class LoginController extends UserHandler{

    @FXML private TextField username;
    @FXML private TextField password;

    @FXML private Button LoginButton;
    @FXML private Button goToCreateAccountPage;

    @FXML
    private void redirectToCreateAccountPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/mindpulse/CreateAccountPage.fxml"));
        Parent MainMenuWindow = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Create Account");
        Scene scene = new Scene(MainMenuWindow, 720, 405);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void login(ActionEvent event) {
        String name = username.getText();
        String pass = password.getText();

        boolean exists = UserHandler.userExists(name, pass);
        if (exists) {
            System.out.println("User exists in the database.");
        } else {
            System.out.println("User does not exist.");
        }

    }
}