module org.project.mindpulse {
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;
    requires jdk.compiler;
    requires javafx.web;


    opens org.project.mindpulse.Controllers to javafx.fxml;

    exports org.project.mindpulse.Application;
}
