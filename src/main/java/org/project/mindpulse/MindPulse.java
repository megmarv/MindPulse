package org.project.mindpulse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.project.mindpulse.SystemManagement.DatabaseHandler;

import java.io.IOException;

public class MindPulse extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/org/project/mindpulse/LoginPage.fxml"));
        primaryStage.setTitle("MindPulse");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
    public static void main(String[] args) {

        DatabaseHandler db = new DatabaseHandler();
        db.createTables();

        launch();
    }
}