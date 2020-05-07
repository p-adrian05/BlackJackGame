package blackJackFX.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import blackJackFX.Main;
import blackJackFX.model.game.Card;
import blackJackFX.model.game.Person;
import blackJackFX.model.Model;
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
public class GameController implements Initializable {
    @FXML
    public Pane imgContainerPlayer2;
    @FXML
    public Pane imgContainerPlayer1;
    @FXML
    private Label betLabel;
    @FXML
    private Label warningLabel;
    @FXML
    private Label resultLabel;
    @FXML
    private Label prizeLabel;
    @FXML
    private Label playerScore;
    @FXML
    private Label playerScore1;
    @FXML
    private Label playerScore2;
    @FXML
    private Label dealerScore;
    @FXML
    private Button dealBtn;
    @FXML
    private Button hitBtn;
    @FXML
    private Button doubleBtn;
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
    private Group playerGroup1;
    @FXML
    private Group playerGroup2;
    @FXML
    private VBox resultPopUpContainer;
    @FXML
    private VBox warningPopUpContainer;
    @FXML
    private VBox newGamePopUpContainer;
    @FXML
    private AnchorPane mainContainer;

    Model model = Model.getInstance();
    boolean splitEnabled = false;

    @FXML
    public void okResultBtnClicked(ActionEvent actionEvent) {
        mainContainer.setDisable(false);
        resultPopUpContainer.setDisable(true);
        resultPopUpContainer.setVisible(false);
        showNewGamePopUp();
        enableHitBtn();
    }
    @FXML
    public void okWarningBtnClicked(ActionEvent actionEvent) {
        mainContainer.setDisable(false);
        warningPopUpContainer.setDisable(true);
        warningPopUpContainer.setVisible(false);
    }
    @FXML
    public void newGameYesBtnClicked(ActionEvent actionEvent) {
        disableNewGamePopUp();
        makeNewRound();
        disableFundAndBetInput(false);
    }
    @FXML
    public void newGameNoBtnClicked(ActionEvent actionEvent) throws IOException {
        disableNewGamePopUp();
        Main.setRoot("login");
    }
    @FXML
    public void dealBtnClicked(ActionEvent actionEvent) {
        disableFundAndBetInput(true);
        loadCardToPerson(2,imgContainerPlayer,model.getPlayer());
        loadCardToPerson(1,imgContainerDealer,model.getDealer());
        try{
            loadCardToPane(new File("card-back.png"),imgContainerDealer);
        }catch (Exception e){
            e.printStackTrace();
        }
        setScoreLabelDealer();
        setScoreLabelPlayer();
        checkBlackJack();
    }
    @FXML
    public void standBtnClicked(ActionEvent actionEvent) {
        disableFundAndBetInput(false);
        dealBtn.setDisable(false);
        loadDealerCards();
        madeResult();
    }
    @FXML
    public void hitBtnClicked(ActionEvent actionEvent) {
        if(splitEnabled){
            hitBtnClickedInSplitMode();
        }
        else{
            loadCardToPerson(1,imgContainerPlayer,model.getPlayer());
            setScoreLabelPlayer();
            checkGameOver();
            checkBlackJack();
        }
    }
    @FXML
    public void doubleBtnClicked(ActionEvent actionEvent) {
        manageBet(model.getPlayer().getBet());
    }
    @FXML
    public void split(ActionEvent actionEvent) {
        if(model.getPlayer().enableSplitCards()){
            playerGroup.setVisible(false);
            playerScore.setVisible(false);
            playerGroup1.setVisible(true);
            playerGroup2.setVisible(true);
            imgContainerPlayer1.getChildren().add(imgContainerPlayer.getChildren().get(1));
            imgContainerPlayer2.getChildren().add(imgContainerPlayer.getChildren().get(1));
            playerScore1.setText(String.valueOf(model.getPlayer().getCardsSumValues()));
            playerScore2.setText(String.valueOf(model.getPlayer().getCardsSumValuesSplit()));
            splitEnabled = true;
        }
    }
    public void hitBtnClickedInSplitMode(){
        if(model.getGameUtils().isScorePass16(model.getPlayer().getCardsSumValues())){
            loadCardToPerson(1,imgContainerPlayer2,model.getPlayer());
            playerScore2.setText(String.valueOf(model.getPlayer().getCardsSumValuesSplit()));
        }
        else{
            loadCardToPerson(1,imgContainerPlayer1,model.getPlayer());
            playerScore1.setText(String.valueOf(model.getPlayer().getCardsSumValues()));
        }
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

    public void showResultPopUp(String result, String prize){
        mainContainer.setDisable(true);
        resultPopUpContainer.setDisable(false);
        resultPopUpContainer.setVisible(true);
        resultLabel.setText(result);
        prizeLabel.setText(prize);
    }
    private void showWarningPopUp(String message){
        mainContainer.setDisable(true);
        warningPopUpContainer.setVisible(true);
        warningPopUpContainer.setDisable(false);
        warningLabel.setText(message);
    }
    public void showNewGamePopUp(){
        mainContainer.setDisable(true);
        newGamePopUpContainer.setVisible(true);
        newGamePopUpContainer.setDisable(false);
    }
    public void disableNewGamePopUp(){
        mainContainer.setDisable(false);
        newGamePopUpContainer.setVisible(false);
        newGamePopUpContainer.setDisable(true);
    }
    private void setScoreLabelPlayer(){
        playerScore.setText(String.valueOf(model.getPlayer().getCardsSumValues()));
    }
    public void setScoreLabelDealer(){
        dealerScore.setText(String.valueOf(model.getDealer().getCardsSumValues()));
    }
    private void disableFundAndBetInput(boolean bool){
        fundInput.setDisable(bool);
        betCoinsContainer.setDisable(bool);
    }
    public void disableHitBtn(){
        hitBtn.setDisable(true);
    }
    public void enableHitBtn(){
        hitBtn.setDisable(false);
    }

    public void madeResult(){
        int playerScore = model.getPlayer().getCardsSumValues();
        int playerScore2 = model.getPlayer().getCardsSumValuesSplit();
        int dealerScore = model.getDealer().getCardsSumValues();
        int[] result = model.getGameUtils().calculateResult(playerScore,playerScore2,dealerScore);
        int prize = model.getGameUtils().calculatePrize(model.getPlayer().getBet(),result);
        if(prize>0){
            model.getPlayer().addFund(prize);
        }
        fundInput.setText(String.valueOf(model.getPlayer().getFund()));
        showResultPopUp(model.getGameUtils().madeStringResult(result),String.valueOf(prize));
    }
    public void makeNewRound(){
        imgContainerDealer.getChildren().remove(1,imgContainerDealer.getChildren().size());
        imgContainerPlayer.getChildren().remove(1,imgContainerPlayer.getChildren().size());
        model.resetValues();
        setScoreLabelDealer();
        setScoreLabelPlayer();
        betLabel.setText("0");
        fundInput.setText(String.valueOf(model.getPlayer().getFund()));
    }
    public void loadDealerCards(){
        imgContainerDealer.getChildren().remove(2);
        while(!model.getGameUtils().isScorePass16(model.getDealer().getCardsSumValues())){
            loadCardToPerson(1,imgContainerDealer,model.getDealer());
            setScoreLabelDealer();
        }
    }
    public void checkGameOver(){
        if(model.getGameUtils().isPlayerScorePass21(model.getPlayer().getCardsSumValues())) {
            disableHitBtn();
            madeResult();
        }
    }
    public void checkBlackJack(){
        if(model.getGameUtils().isBlackJack(model.getPlayer().getCardsSumValues())){
            madeResult();
            playerScore.setText("BLACKJACK");
        }
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

    public void loadCardToPerson(int amount, Pane placetoLoad, Person person){
        ImageView imageView;
        Card card;
        for(int i = 0; i<amount;i++) {
            card = model.getDeck().getCard();
            imageView = madeImageViewFromUrl(card.getImageUrl().toString(), getLastChildXLayout(placetoLoad) + 25);
            placetoLoad.getChildren().add(imageView);
            person.addCard(card);
        }
    }
    private double getLastChildXLayout(Pane pane){
        double xLayout;
        int lastChildIndex = pane.getChildren().size()-1;
        if(lastChildIndex==0){
            xLayout = 0;
        }else{
            xLayout = pane.getChildren().get(lastChildIndex).getLayoutX();
        }
        return xLayout;
    }
    private ImageView madeImageViewFromUrl(String url,double imgXLayout){
        ImageView imageView = new ImageView();
        imageView.setFitHeight(139);
        imageView.setFitWidth(100);
        imageView.setLayoutX(imgXLayout);
        imageView.setImage(new Image(url));
        return imageView;
    }

    public void loadCardToPane(File file, Pane placetoLoad){
        ImageView imageView;
            imageView = madeImageViewFromUrl(file.toString(), getLastChildXLayout(placetoLoad) + 25);
            placetoLoad.getChildren().add(imageView);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        readInFundInputListener();
    }


}
