module BlackJackFX {
    requires javafx.controls;
    requires javafx.fxml;

    opens BlackJackFX to javafx.fxml;
    exports BlackJackFX;
}