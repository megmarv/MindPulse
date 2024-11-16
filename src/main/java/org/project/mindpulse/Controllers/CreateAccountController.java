package org.project.mindpulse.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateAccountController {

    @FXML
    private Button goToLoginPage;

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
}