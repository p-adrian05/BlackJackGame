package blackJackFX.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import blackJackFX.Main;
import blackJackFX.Model.Game.Card;
import blackJackFX.Model.Game.Person;
import blackJackFX.Model.Model;
import com.google.gson.internal.$Gson$Preconditions;
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
    private Label resultLabel;
    @FXML
    private Label prizeLabel;
    @FXML
    private Label playerScore;
    @FXML
    private Label dealerScore;
    @FXML
    private Button dealBtn;
    @FXML
    private Button hitBtn;
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
    private VBox resultPopUpContainer;
    @FXML
    private VBox warningPopUpContainer;
    @FXML
    private VBox newGamePopUpContainer;

    @FXML
    private AnchorPane mainContainer;

    Model model = Model.getInstance();

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
    public void dealBtnClicked(ActionEvent actionEvent) {
        disableFundAndBetInput(true);
        loadCardAndScoreToPerson(2,imgContainerPlayer,model.getPlayer());
        loadCardAndScoreToPerson(2,imgContainerDealer,model.getDealer());
        setScoreLabelDealer();
        setScoreLabelPlayer();
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
        dealBtn.setDisable(false);
        loadDealerCards();
        madeResult();
    }
    @FXML
    public void split(ActionEvent actionEvent) {
    }
    @FXML
    public void hitBtnClicked(ActionEvent actionEvent) {
        loadCardAndScoreToPerson(1,imgContainerPlayer,model.getPlayer());
        setScoreLabelPlayer();
        checkGameOver();
    }

    public void showResultPopUp(String result, String prize){
        mainContainer.setDisable(true);
        resultPopUpContainer.setDisable(false);
        resultPopUpContainer.setVisible(true);
        resultLabel.setText(result);
        prizeLabel.setText(prize);
    }
    public void madeResult(){
        int playerScore = model.getPlayer().getCardsSumValues();
        int dealerScore = model.getDealer().getCardsSumValues();
        int result = model.getGameUtils().calculateResult(playerScore,dealerScore);
        int prize = model.getGameUtils().calculatePrize(model.getPlayer().getBet(),result);
        model.getPlayer().addFund(prize);
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

    }
    public void disableHitBtn(){
        hitBtn.setDisable(true);
    }
    public void enableHitBtn(){
        hitBtn.setDisable(false);
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
    public void loadCardAndScoreToPerson(int amount, Pane placetoLoad, Person person){
        ImageView imageView;
        Card card;
        for(int i = 0; i<amount;i++) {
            card = model.getDeck().getCard();
            imageView = madeImageViewFromUrl(card.getImageUrl().toString(), getLastChildXLayout(placetoLoad) + 25);
            placetoLoad.getChildren().add(imageView);
            person.addCard(card);
            person.setCardsSumValues(model.getDeck().calcCardsSumValue(person.getCards()));
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
    private void setScoreLabelPlayer(){
        playerScore.setText(String.valueOf(model.getPlayer().getCardsSumValues()));
    }
    public void setScoreLabelDealer(){
        dealerScore.setText(String.valueOf(model.getDealer().getCardsSumValues()));
    }
    public void loadDealerCards(){
        while(!model.getGameUtils().isDealerScorePass16(model.getDealer().getCardsSumValues())){
            loadCardAndScoreToPerson(1,imgContainerDealer,model.getDealer());
            setScoreLabelDealer();
        }
    }
    private void showWarningPopUp(String message){
        mainContainer.setDisable(true);
        warningPopUpContainer.setVisible(true);
        warningPopUpContainer.setDisable(false);
        warningLabel.setText(message);
    }
    public void checkGameOver(){
        if(model.getGameUtils().isPlayerScorePass21(model.getPlayer().getCardsSumValues())) {
             disableHitBtn();
             madeResult();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        readInFundInputListener();
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

    public void newGameYesBtnClicked(ActionEvent actionEvent) {
        disableNewGamePopUp();
        makeNewRound();

    }

    public void newGameNoBtnClicked(ActionEvent actionEvent) throws IOException {
        disableNewGamePopUp();
        Main.setRoot("login");
    }
}
