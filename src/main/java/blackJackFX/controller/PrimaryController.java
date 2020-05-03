package blackJackFX.controller;

import java.net.URL;
import java.util.ResourceBundle;

import blackJackFX.Model.Game.Card;
import blackJackFX.Model.Game.Deck;
import blackJackFX.Model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
    private HBox betCoinsContainer;
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

    Model model = Model.getInstance();
    int imgXLayoutPlayer = 0;
    int imgXLayoutDealer = 0;


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
        disableFundAndBetInput(true);
        loadCardToPerson(model.getDeck(),2,imgContainerPlayer);
        loadCardToPerson(model.getDeck(),2,imgContainerDealer);
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
        disableFundAndBetInput(false);
    }
    @FXML
    public void split(ActionEvent actionEvent) {
    }
    @FXML
    public void hitBtnClicked(ActionEvent actionEvent) {
    }
    @FXML
    public void coin80Clicked(MouseEvent mouseEvent) {
        manageBet(80);
    }
    @FXML
    public void coin40Clicked(MouseEvent mouseEvent) {
        manageBet(40);
    }
    @FXML
    public void coin20Clicked(MouseEvent mouseEvent) {
        manageBet(20);
    }
    @FXML
    public void coin10Clicked(MouseEvent mouseEvent) {
        manageBet(10);
    }
    private void readInFundInputListener(){
        fundInput.textProperty().addListener((obs, oldText, newText) -> {
            if (!newText.matches("\\d*")) {
                fundInput.setText(newText.replaceAll("[^\\d]", ""));
            }else {
                if(!fundInput.textProperty().getValue().equals("")){
                    model.getPlayer().setFund(Integer.parseInt(fundInput.textProperty().getValue()));
                }
            }
        });
    }
    private void manageBet(int value){
        if(model.getGameUtils().validateBet(value,model.getPlayer().getFund())){
            model.getPlayer().addBetFromFund(value);
            betLabel.setText(String.valueOf(model.getPlayer().getBet()));
            fundInput.textProperty().setValue(String.valueOf(model.getPlayer().getFund()));
        }
        else{
            showWarningPopUp("Not have enough funds!");
        }
    }
    private void disableFundAndBetInput(boolean bool){
        fundInput.setDisable(bool);
        betCoinsContainer.setDisable(bool);
    }

    private void loadCardToPerson(Deck deck, int amount,Pane placetoLoad){
        ImageView imageView;
        Card card;
        for(int i = 0; i<amount;i++){
            card = deck.getCard();
            imageView = madeImageViewFromUrl(card.getImageUrl().toString(),imgXLayoutPlayer+=30);
            placetoLoad.getChildren().add(imageView);
            model.getPlayer().addCard(card);
        }
    }

    private ImageView madeImageViewFromUrl(String url,int imgXLayout){
        ImageView imageView = new ImageView();
        imageView.setFitHeight(139);
        imageView.setFitWidth(100);
        imageView.setLayoutX(imgXLayout);
        imageView.setImage(new Image(url));
        return imageView;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        readInFundInputListener();
    }


}
