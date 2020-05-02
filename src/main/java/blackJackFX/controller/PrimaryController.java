package blackJackFX.controller;

import java.net.URL;
import java.util.ResourceBundle;

import blackJackFX.Game.Dealer;
import blackJackFX.Game.GameUtils;
import blackJackFX.Game.Player;
import javafx.beans.value.ObservableValue;
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

import javax.swing.event.ChangeListener;

@Log4j2
public class PrimaryController implements Initializable {
    @FXML
    private Label betLabel;
    @FXML
    public Label warningLabel;
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

    public Player player = new Player();
    public Dealer dealer = new Dealer();
    private int fund = 0;
    private int bet = 0;

    public void okBtnClicked(ActionEvent actionEvent) {
    }

    public void okWarningBtnClicked(ActionEvent actionEvent) {
        mainContainer.setDisable(false);
        warningPopUpContainer.setDisable(true);
        warningPopUpContainer.setVisible(false);

    }

    public void logOutClick(ActionEvent actionEvent) {
    }

    public void helpClick(ActionEvent actionEvent) {
    }

    public void gameStatisticClick(ActionEvent actionEvent) {
    }

    public void editNameClicked(ActionEvent actionEvent) {
    }

    public void MenuBarClicked(MouseEvent mouseEvent) {
    }

    public void standBtnClicked(ActionEvent actionEvent) {
    }

    public void split(ActionEvent actionEvent) {
    }

    public void imgLoad(ActionEvent actionEvent) {
    }

    public void coin80Clicked(MouseEvent mouseEvent) {
    }

    public void coin40Clicked(MouseEvent mouseEvent) {
    }

    public void coin20Clicked(MouseEvent mouseEvent) {
    }
    public void coin10Clicked(MouseEvent mouseEvent) {
        bet+=10;
        setLabelText(bet);

    }
    private void madeFundInputEventListener(){
        fundInput.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) {
                fundInput.setText(newText.replaceAll("[^\\d]", ""));
            }else {
                fund = Integer.parseInt(fundInput.textProperty().getValue());
                player.setFunds(fund);
            }
        });
    }

    private void setLabelText(int value){
        if(GameUtils.validateBet(value,fund)){
            betLabel.setText(String.valueOf(value));
            player.setBet(value);
        }
        else{
            //TODO warning
        }

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        madeFundInputEventListener();
    }

}
