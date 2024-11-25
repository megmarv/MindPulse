package org.project.mindpulse.Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.project.mindpulse.SystemManagement.DatabaseHandler;

public class MindPulse extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/org/project/mindpulse/FirstPage.fxml"));
        primaryStage.setTitle("MindPulse");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}