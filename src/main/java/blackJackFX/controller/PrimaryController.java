package blackJackFX.controller;

import java.net.URL;
import java.util.ResourceBundle;

import blackJackFX.Model.Game.GameUtils;
import blackJackFX.Model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PrimaryController implements Initializable {
    @FXML
    private Label betLabel;
    @FXML
    private Label warningLabel;
    @FXML
    private TextField fundInput;
    @FXML
    private Pane imgContainerDealer;
    @FXML
    private Pane imgContainerPlayer;
    @FXML
    private Group playerGroup;
    @FXML
    private Group playerSplitGroup1;
    @FXML
    private Group playerSplitGroup2;
    @FXML
    private VBox popUpContainer;
    @FXML
    private VBox warningPopUpContainer;
    @FXML
    private AnchorPane mainContainer;

    private int fund = 0;
    private int bet = 0;
    Model model = Model.getInstance();

    @FXML
    public void okBtnClicked(ActionEvent actionEvent) {
    }
    @FXML
    public void okWarningBtnClicked(ActionEvent actionEvent) {
        mainContainer.setDisable(false);
        warningPopUpContainer.setDisable(true);
        warningPopUpContainer.setVisible(false);
    }
    @FXML
    private void showWarningPopUp(String message){
        mainContainer.setDisable(true);
        warningPopUpContainer.setVisible(true);
        warningPopUpContainer.setDisable(false);
        warningLabel.setText(message);
    }
    @FXML
    public void dealBtnClicked(ActionEvent actionEvent) {
    }
    @FXML
    public void logOutClick(ActionEvent actionEvent) {
    }
    @FXML
    public void helpClick(ActionEvent actionEvent) {
    }
    @FXML
    public void gameStatisticClick(ActionEvent actionEvent) {
    }
    @FXML
    public void editNameClicked(ActionEvent actionEvent) {
    }
    @FXML
    public void MenuBarClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void standBtnClicked(ActionEvent actionEvent) {
    }
    @FXML
    public void split(ActionEvent actionEvent) {
    }
    @FXML
    public void imgLoad(ActionEvent actionEvent) {
    }
    @FXML
    public void coin80Clicked(MouseEvent mouseEvent) {
        manageBetValue(80);
    }
    @FXML
    public void coin40Clicked(MouseEvent mouseEvent) {
        manageBetValue(40);
    }
    @FXML
    public void coin20Clicked(MouseEvent mouseEvent) {
        manageBetValue(20);
    }
    @FXML
    public void coin10Clicked(MouseEvent mouseEvent) {
        manageBetValue(10);
    }
    private void readInFundInputListener(){
        fundInput.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) {
                fundInput.setText(newText.replaceAll("[^\\d]", ""));
            }else {
                if(!fundInput.textProperty().getValue().equals("")){
                    fund = Integer.parseInt(fundInput.textProperty().getValue());
                    model.getPlayer().setFunds(fund);
                }

            }
        });
    }
    private void manageBetValue(int value){
        if(GameUtils.validateBet(value,model.getPlayer().getFunds())){
            betLabel.setText(String.valueOf(bet+=value));
            fundInput.textProperty().setValue(String.valueOf(fund-=value));
            model.getPlayer().setBet(bet);
            model.getPlayer().setFunds(fund);
        }
        else{
            showWarningPopUp("Not have enough funds!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        readInFundInputListener();
    }

}
