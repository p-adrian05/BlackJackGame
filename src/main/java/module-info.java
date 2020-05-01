module blackJackFX {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires com.google.gson;
    requires org.apache.commons.io;

    opens blackJackFX to javafx.fxml;
    exports blackJackFX;
}