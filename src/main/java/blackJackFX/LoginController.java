package blackJackFX;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LoginController {

    @FXML
    private void switchToSecondary() throws IOException {
        Main.setRoot("secondary");
    }

    public void loginBtnClicked(ActionEvent actionEvent) {
    }
}
