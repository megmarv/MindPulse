module org.project.mindpulse {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
    requires java.sql;
    requires javafx.media;

    opens org.project.mindpulse.Controllers to javafx.fxml;

    exports org.project.mindpulse;
}
