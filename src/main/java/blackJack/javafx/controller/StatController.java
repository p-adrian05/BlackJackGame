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

    public void initPies(){
        madePie(new int[]{model.getUser().getWonCount(),
                model.getUser().getLoseCount()},"Won/Lost rate",pieLoseWinCount);
        madePie(new int[]{model.getUser().getWonMoney(),
                model.getUser().getLostMoney()},"Won/Lost money rate",pieLoseWinMoney);
    }
    public void madePie(int[] datas, String title, PieChart pie ) {
        log.info("Setting pie: "+title);
        log.debug("WON: {}",datas[0]);
        log.debug("LOST: {}",datas[1]);
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Won", Math.abs(datas[0])),
                        new PieChart.Data("Lost",  Math.abs(datas[1])));
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ",  data.pieValueProperty().intValue())
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
        initPies();
    }
}
