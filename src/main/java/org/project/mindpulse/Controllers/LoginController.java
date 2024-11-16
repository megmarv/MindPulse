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

public class LoginController {

    @FXML
    private Button goToCreateAccountPage;

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

}