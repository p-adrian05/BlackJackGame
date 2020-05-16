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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatController implements Initializable {
    @FXML
    private PieChart pieLoseWinCount;
    @FXML
    private PieChart pieLoseWinMoney;

    Model model = Model.getInstance();

    public void setPieLoseWinCount() {
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
