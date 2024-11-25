package org.project.mindpulse.Controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.project.mindpulse.SystemManagement.UserHandler;

import java.io.IOException;

public class AdminLoginController implements GeneralFeatures{

    @FXML private TextField username;
    @FXML private TextField password;

    @FXML private Button exit;
    @FXML private Button LoginButton;

    @FXML
    private void login(ActionEvent event) throws IOException {
        String name = username.getText();
        String pass = password.getText();

        boolean exists = UserHandler.userExists(name, pass);
        if (exists) {

            System.out.println("User exists in the database.");

            redirectToHomePage(event);

        } else {
            System.out.println("User does not exist.");
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
