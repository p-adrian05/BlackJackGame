module blackJackFX {
    requires javafx.controls;
    requires javafx.fxml;

    opens fxml to javafx.fxml;
    exports blackJackFX;
}