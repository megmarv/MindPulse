package org.project.mindpulse.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.project.mindpulse.SystemManagement.UserHandler;

import java.io.IOException;

public class CreateAccountController extends UserHandler implements GeneralFeatures{

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;

    @FXML private Button exit;
    @FXML private Button createAccountButton;
    @FXML private Button goToLoginPage;

    @FXML
    private void redirectToLogInPage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/mindpulse/UserLogin.fxml"));
        Parent MainMenuWindow = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Sign in");
        Scene scene = new Scene(MainMenuWindow, 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void createNewAccount(ActionEvent event) throws IOException {
        String name = nameField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();

        boolean isUserCreated = UserHandler.createNewUser(name, email, username, password);
        if (isUserCreated) {
            System.out.println("Account created successfully!");
            // Redirect to login page or show a success message

            redirectToLogInPage(event);

        } else {
            System.out.println("Username or email already exists. Please try again.");
            // Show an error message to the user
        }
    }

    @FXML
    public void displayConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void displayError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void Exit(ActionEvent exit) throws IOException {

        Platform.exit();

    }

    @FXML
    private void redirectToHomePage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/project/mindpulse/HomePage.fxml"));
        Parent MainMenuWindow = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Home");
        Scene scene = new Scene(MainMenuWindow, 1100, 600);
        stage.setScene(scene);
        stage.show();
    }

}