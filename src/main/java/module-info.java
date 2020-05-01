module blackJackFX {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    opens blackJackFX to javafx.fxml;
    exports blackJackFX;
}