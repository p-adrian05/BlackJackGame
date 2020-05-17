package blackJack.javafx.controller;

import blackJack.javafx.BlackJackApplication;
import blackJack.model.Model;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Menu;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class StatController implements Initializable {
    @FXML
    private PieChart pieLoseWinCount;
    @FXML
    private PieChart pieLoseWinMoney;

    Model model = Model.getInstance();

    public void setPieLoseWinCount() {
        log.info("Setting LoseWinCount pie");
        log.debug("WON: {}",model.getUser().getWonCount());
        log.debug("LOST: {}",model.getUser().getLoseCount());
        ObservableList<PieChart.Data> pieChartDataLoseWin =
                FXCollections.observableArrayList(
                        new PieChart.Data("Won", model.getUser().getWonCount()),
                        new PieChart.Data("Lost", model.getUser().getLoseCount()));

        pieChartDataLoseWin.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ",  data.pieValueProperty().intValue())
                )
        );

        pieLoseWinCount.setData(pieChartDataLoseWin);
        pieLoseWinCount.setTitle("Won/Lost rate");
    }

    public void setPieLoseWinMoney(){
        log.info("Setting LoseWinMoney pie");
        log.debug("WON: {}",model.getUser().getWonMoney());
        log.debug("LOST: {}",model.getUser().getLostMoney());
        ObservableList<PieChart.Data> pieChartDataMoney =
                FXCollections.observableArrayList(
                        new PieChart.Data("Won", model.getUser().getWonMoney()),
                        new PieChart.Data("Lost", Math.abs(model.getUser().getLostMoney())));

        pieChartDataMoney.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ",  data.pieValueProperty().intValue())
                )
        );

        pieLoseWinMoney.setData(pieChartDataMoney);
        pieLoseWinMoney.setTitle("Won/Lost money rate");

    }

    public void backGameBtnClicked(ActionEvent actionEvent) {
        log.info("Back to game button clicked");
        try {
            BlackJackApplication.setRoot("primary");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPieLoseWinCount();
        setPieLoseWinMoney();
    }
}
