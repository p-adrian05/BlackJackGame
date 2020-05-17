package blackJack.javafx.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import blackJack.javafx.BlackJackApplication;
import blackJack.model.game.Card;
import blackJack.model.game.Person;
import blackJack.model.Model;
import blackJack.model.game.Result;
import blackJack.results.User;
import blackJack.results.UserDao;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import net.bytebuddy.matcher.ModifierMatcher;

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
    private Label playerNameLabel;
    @FXML
    private Button dealBtn;
    @FXML
    private Button hitBtn;
    @FXML
    private Button doubleBtn;
    @FXML
    private Button splitBtn;
    @FXML
    private Button standBtn;
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
    boolean standButtonClicked = false;

    @FXML
    public void okResultBtnClicked(ActionEvent actionEvent) {
        mainContainer.setDisable(false);
        resultPopUpContainer.setDisable(true);
        resultPopUpContainer.setVisible(false);
        showNewGamePopUp();
        log.info("OK button clicked on result pop up.");
    }
    @FXML
    public void okWarningBtnClicked(ActionEvent actionEvent) {
        mainContainer.setDisable(false);
        warningPopUpContainer.setDisable(true);
        warningPopUpContainer.setVisible(false);
        log.info("OK button clicked on warning pop up.");
    }
    @FXML
    public void newGameYesBtnClicked(ActionEvent actionEvent) {
        disableNewGamePopUp();
        makeNewRound();
        disableFundAndBetInput(false);
        log.info("Yes button clicked on new game pop up.");
    }
    @FXML
    public void newGameNoBtnClicked(ActionEvent actionEvent){
        disableNewGamePopUp();
        try {
            BlackJackApplication.setRoot("login");
        }catch (IOException e) {
            e.printStackTrace();
        }
        log.info("No button clicked on new game pop up.");
    }
    @FXML
    public void dealBtnClicked(ActionEvent actionEvent) {
        log.info("Deal button clicked.");
        disableFundAndBetInput(true);
        disableAllBtn(false);
        dealBtn.setDisable(true);
        loadCardToPerson(2,imgContainerPlayer,model.getPlayer());
        loadCardToPerson(1,imgContainerDealer,model.getDealer());
        loadCardToPane(new File("images/card-back.png"),imgContainerDealer);
        setScoreLabelDealer();
        setScoreLabelPlayer();
        checkBlackJack();
        checkSplitEnable();
    }
    @FXML
    public void standBtnClicked(ActionEvent actionEvent) {
        log.info("Stand button clicked.");
        if((splitEnabled && standButtonClicked) ||
                (splitEnabled && model.getPlayer().getCardsSumValues()>=21) || !splitEnabled){
            disableFundAndBetInput(false);
            hitBtn.setDisable(true);
            loadDealerCards();
            madeResult();
        }else{
            standButtonClicked = true;
        }

    }
    @FXML
    public void hitBtnClicked(ActionEvent actionEvent) {
        log.info("Hit button clicked.");
        if(splitEnabled){
            hitBtnClickedInSplitMode();
        }
        else{
            loadCardToPerson(1,imgContainerPlayer,model.getPlayer());
            setScoreLabelPlayer();
            checkGameOver();
        }
        checkBlackJack();
    }
    @FXML
    public void doubleBtnClicked(ActionEvent actionEvent) {
        log.info("Double button clicked");
        if(model.getPlayer().getCards().size()==2){
            manageBet(model.getPlayer().getBet());
            dealBtn.setDisable(true);
        }else{
            showWarningPopUp("Double not allowed!");
        }
        doubleBtn.setDisable(true);
    }
    @FXML
    public void splitBtnClicked(ActionEvent actionEvent) {
        log.info("Split button clicked");
        if(model.getPlayer().isEnableSplitCards() && manageBet(model.getPlayer().getBet())){
            enableSplitLayout(true);
            model.getPlayer().madeSplitCards();
            imgContainerPlayer1.getChildren().add(imgContainerPlayer.getChildren().get(1));
            imgContainerPlayer2.getChildren().add(imgContainerPlayer.getChildren().get(1));
            playerScore1.setText(String.valueOf(model.getPlayer().getCardsSumValues()));
            playerScore2.setText(String.valueOf(model.getPlayer().getCardsSumValuesSplit()));
            splitEnabled = true;
            doubleBtn.setDisable(true);
        }else{
            showWarningPopUp("Cards splitting not allowed!");
        }
        log.info("Split enabled: {}",splitEnabled);
        splitBtn.setDisable(true);
        dealBtn.setDisable(true);
    }
    public void hitBtnClickedInSplitMode(){
        if(standButtonClicked || model.getPlayer().getCardsSumValues()>=21){
            model.getPlayer().madeSecondHand();
            loadCardToPerson(1,imgContainerPlayer2,model.getPlayer());
            playerScore2.setText(String.valueOf(model.getPlayer().getCardsSumValuesSplit()));
            log.info("Player score 2: {}",playerScore2);
        }
        else{
            loadCardToPerson(1,imgContainerPlayer1,model.getPlayer());
            playerScore1.setText(String.valueOf(model.getPlayer().getCardsSumValues()));
            log.info("Player score 1: {}",playerScore1);
        }
        checkGameOver();
        checkBlackJack();
    }
    @FXML
    public void logOutClick(ActionEvent actionEvent) {
        log.info("Log out has happened.");
        try {
           BlackJackApplication.setRoot("login");
        } catch (IOException e) {
            e.printStackTrace();
        }
        makeNewRound();
    }
    @FXML
    public void gameStatisticClick(ActionEvent actionEvent) {
        log.info("Game statistic button clicked");
        try {
            BlackJackApplication.setRoot("stat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void editNameClicked(ActionEvent actionEvent) {
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
        log.debug("Player score: {}", model.getPlayer().getCardsSumValues());
    }
    public void setScoreLabelDealer(){
        dealerScore.setText(String.valueOf(model.getDealer().getCardsSumValues()));
        log.debug("Dealer score: {}", model.getDealer().getCardsSumValues());
    }
    private void disableFundAndBetInput(boolean bool){
        fundInput.setDisable(bool);
        betCoinsContainer.setDisable(bool);
    }
    public void enableSplitLayout(boolean bool){
        if(bool){
            playerGroup.setVisible(false);
            playerScore.setVisible(false);
            playerGroup1.setVisible(true);
            playerGroup2.setVisible(true);
        }else{
            playerGroup.setVisible(true);
            playerScore.setVisible(true);
            playerGroup1.setVisible(false);
            playerGroup2.setVisible(false);
        }
    }
    public void disableAllBtn(boolean bool){
        dealBtn.setDisable(bool);
        hitBtn.setDisable(bool);
        splitBtn.setDisable(bool);
        doubleBtn.setDisable(bool);
        standBtn.setDisable(bool);
    }
    public void checkSplitEnable(){
        log.info("Checking split button allowing...");
        if(model.getPlayer().isEnableSplitCards()){
            activateBtn(splitBtn);
            log.info("Split button allowed.");
        }else{
            deactivateBtn(splitBtn);
            log.info("Split button not allowed.");
        }
    }
    public void madeResult(){
        Result[] results = model.getResult();
        int prize = model.getPrize(results);
        showResultPopUp(model.getGameUtils().madeStringResult(results), String.valueOf(prize));
        fundInput.textProperty().setValue((String.valueOf(model.getPlayer().getFund())));
        model.saveUser();
        log.info("RESULT: {}", resultLabel.getText());
        log.info("PRIZE: {}", prizeLabel.getText());
        log.info("Player fund: {}", fundInput.getText());
    }
    public void makeNewRound(){
        imgContainerDealer.getChildren().remove(1,imgContainerDealer.getChildren().size());
        imgContainerPlayer.getChildren().remove(1,imgContainerPlayer.getChildren().size());
        if(splitEnabled){
            imgContainerPlayer1.getChildren().remove(1,imgContainerPlayer1.getChildren().size());
            imgContainerPlayer2.getChildren().remove(1,imgContainerPlayer2.getChildren().size());
        }
        model.resetGame();
        setScoreLabelDealer();
        setScoreLabelPlayer();
        betLabel.setText("0");
        fundInput.textProperty().setValue(String.valueOf(model.getUser().getFunds()));
        model.getPlayer().setFund(model.getUser().getFunds());
        enableSplitLayout(false);
        splitEnabled = false;
        disableAllBtn(true);
        standButtonClicked = false;
        log.info("Made new round.");
    }
    public void loadDealerCards(){
        log.info("Dealer getting cards: ");
        imgContainerDealer.getChildren().remove(2);
        while(model.getDealer().getCardsSumValues()<17){
            loadCardToPerson(1,imgContainerDealer,model.getDealer());
            setScoreLabelDealer();
        }
    }
    public void checkGameOver(){
        log.info("Checking game over...");
        if (model.isGameOver()) {
            madeResult();
        }
        if(splitEnabled && model.isSplitGameOver()){
            loadDealerCards();
            madeResult();
        }
    }
    public void checkBlackJack(){
        log.info("Checking BlackJack has happened...");
        boolean isBjNumber = model.getPlayer().getCardsSumValues()==21;
        if(splitEnabled){
            boolean isBjNumber2 = model.getPlayer().getCardsSumValuesSplit()==21;
            if(isBjNumber){
                playerScore1.setText("BLACKJACK");
            }
            if(isBjNumber2){
                if(!isBjNumber){
                    loadDealerCards();
                }
                madeResult();
                playerScore2.setText("BLACKJACK");
            }
        }else{
            if(isBjNumber){
                madeResult();
                playerScore.setText("BLACKJACK");
            }
        }
    }
    private void readInFundInputListener(){
        fundInput.textProperty().addListener((obs, oldText, newText) -> {
            log.debug("Fund input text: {}",fundInput.textProperty().getValue());
            if (!newText.matches("\\d*")) {
                fundInput.setText(newText.replaceAll("[^\\d]", ""));
            }else if(!fundInput.textProperty().getValue().equals("")){
                model.getPlayer().setFund(Integer.parseInt(fundInput.textProperty().getValue()));

            }
        });
    }
    private boolean manageBet(int value){
        if(model.getGameUtils().validateBet(value,model.getPlayer().getFund())){
            log.info("Player's valid bet: {}",value);
            model.getPlayer().addBetFromFund(value);
            betLabel.setText(String.valueOf(model.getPlayer().getBet()));
            fundInput.textProperty().setValue(String.valueOf(model.getPlayer().getFund()));
            dealBtn.setDisable(false);
            return true;
        }
        else{
            showWarningPopUp("Not have enough funds!");
            log.info("Player's not valid bet: ",value);
            return false;
        }
    }
    public void loadCardToPerson(int amount, Pane placetoLoad, Person person){
        ImageView imageView;
        Card card;
        for(int i = 0; i<amount;i++) {
            card = model.getDeck().getCard();
            log.debug("Card object: {}",card.toString());
            try{
                imageView = madeImageViewFromUrl(card.getImageUrl().toString(), getLastChildXLayout(placetoLoad) + 25);
                log.info("Card image URL: {}",imageView.getImage().getUrl());
                placetoLoad.getChildren().add(imageView);
            }catch (Exception ex){
                log.error(ex.getMessage(),ex);
                showWarningPopUp("Failed to load image");
                placetoLoad.getChildren().add(madeLabel(getLastChildXLayout(placetoLoad),card.getCode()));
            }
            person.addCard(card);
        }
    }
    private Label madeLabel(double xLayout,String msg){
        Label label = new Label();
        label.setLayoutX(xLayout + 35);
        label.setText(msg);
        label.setTextFill(Color.RED);
        label.setStyle("-fx-font-size: 15px");
        log.info("Card code label text: {}",label.getText());
        return label;
    }
    private double getLastChildXLayout(Pane pane){
        double xLayout;
        int lastChildIndex = pane.getChildren().size()-1;
        if(lastChildIndex==0){
            xLayout = 0;
        }else{
            xLayout = pane.getChildren().get(lastChildIndex).getLayoutX();
        }
        log.debug("Image xLayout: {}",xLayout);
        return xLayout;
    }
    private ImageView madeImageViewFromUrl(String url,double imgXLayout) throws Exception {
        ImageView imageView = new ImageView();
        imageView.setFitHeight(139);
        imageView.setFitWidth(100);
        imageView.setLayoutX(imgXLayout);
        imageView.setImage(new Image(url));
        if(imageView.getImage().getException() != null){
            throw imageView.getImage().getException();
        }
        return imageView;
    }
    public void loadCardToPane(File file, Pane placetoLoad){
        ImageView imageView;
        try {
            imageView = madeImageViewFromUrl(file.toString(), getLastChildXLayout(placetoLoad) + 25);
            placetoLoad.getChildren().add(imageView);
            log.info("Image URL: {}",imageView.getImage().getUrl());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            placetoLoad.getChildren().add(madeLabel(getLastChildXLayout(placetoLoad),"err"));
            showWarningPopUp("Failed to load images");
        }
    }
    private void activateBtn(Button btn){
        btn.getStyleClass().add("redBorder");
        btn.setDisable(false);
    }
    private void deactivateBtn(Button btn){
        btn.setDisable(true);
        btn.getStyleClass().remove("redBorder");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        readInFundInputListener();
        disableAllBtn(true);
        playerNameLabel.setText(model.getUser().getUsername());
        fundInput.textProperty().setValue(String.valueOf(model.getUser().getFunds()));
        log.info("INIT game controller");
    }
}
