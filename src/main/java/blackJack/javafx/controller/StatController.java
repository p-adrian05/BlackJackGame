package blackJack.javafx.controller;

import blackJack.model.Model;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
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

    @Inject
    private FXMLLoader fxmlLoader;

    Model model = Model.getInstance();

    public void initData(){
        madePie(new int[]{model.getUser().getWonCount(),
                model.getUser().getLoseCount()},"Won/Lost rate",pieLoseWinCount);
        madePie(new int[]{model.getUser().getWonMoney(),
                model.getUser().getLostMoney()},"Won/Lost money rate",pieLoseWinMoney);
        maxBetLabel.setText(String.valueOf(model.getUser().getMaxBet()));
        totalProfitLabel.setText(String.valueOf(model.getUser().getWonMoney() + model.getUser().getLostMoney()));
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
        fxmlLoader.setLocation(getClass().getResource("/fxml/primary.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("fxml/main.css");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
    }
}
