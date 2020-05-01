package blackJackFX.Controller;

import blackJackFX.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Menu;

import java.io.IOException;

public class StatController {
    @FXML
    private Menu statisticMenu;
    @FXML
    private PieChart pieLoseWinCount;
    @FXML
    private PieChart pieLoseWinMoney;

    public void backGameBtnClicked(ActionEvent actionEvent) {
        try {
            Main.setRoot("primary");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
