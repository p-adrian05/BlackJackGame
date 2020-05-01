module blackJackFX {
    requires javafx.controls;
    requires javafx.fxml;

    opens blackJackFX to javafx.fxml;
    exports blackJackFX;
}