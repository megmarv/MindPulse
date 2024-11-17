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
import java.sql.SQLException;

public class CreateAccountController extends UserHandler {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;

    @FXML private Button createAccountButton;
    @FXML private Button goToLoginPage;

    @FXML
    private void redirectToLogInPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/mindpulse/LoginPage.fxml"));
        Parent MainMenuWindow = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Sign in");
        Scene scene = new Scene(MainMenuWindow, 720, 405);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void createNewAccount(ActionEvent event) {
        String name = nameField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean isUserCreated = UserHandler.createNewUser(name, email, username, password);
        if (isUserCreated) {
            System.out.println("Account created successfully!");
            // Redirect to login page or show a success message
        } else {
            System.out.println("Username or email already exists. Please try again.");
            // Show an error message to the user
        }
    }

}