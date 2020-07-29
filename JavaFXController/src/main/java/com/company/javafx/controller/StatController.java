package com.company.javafx.controller;

import com.company.blackJack.GameService;
import com.company.javafx.BlackJackApplication;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Slf4j
public class StatController implements Initializable {
    @FXML
    private PieChart pieLoseWinCount;
    @FXML
    private PieChart pieLoseWinMoney;
    @FXML
    private Label maxBetLabel;
    @FXML
    private Label totalProfitLabel;

    @Autowired
    GameService gameService;

    public void initData(){
        madePie(new int[]{gameService.getUser().getWonCount(),
                gameService.getUser().getLoseCount()},"Won/Lost rate",pieLoseWinCount);
        madePie(new int[]{gameService.getUser().getWonMoney(),
                gameService.getUser().getLostMoney()},"Won/Lost money rate",pieLoseWinMoney);
        maxBetLabel.setText(String.valueOf(gameService.getUser().getMaxBet()));
        totalProfitLabel.setText(String.valueOf(gameService.getUser().getWonMoney() + gameService.getUser().getLostMoney()));
    }
    public void madePie(int[] data, String title, PieChart pie ) {
        log.info("Setting pie: "+title);
        log.debug("WON: {}",data[0]);
        log.debug("LOST: {}",data[1]);
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Won", Math.abs(data[0])),
                        new PieChart.Data("Lost",  Math.abs(data[1])));
        pieChartData.forEach(item ->
                item.nameProperty().bind(
                        Bindings.concat(
                                item.getName(), " ",  item.pieValueProperty().intValue())
                )
        );
        pie.setData(pieChartData);
        pie.setTitle(title);
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
        initData();
    }
}
