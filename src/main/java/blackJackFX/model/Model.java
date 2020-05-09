package blackJackFX.model;

import blackJackFX.model.game.*;
public class Model {

    private static final Model model = new Model();

    private Player player;
    private Person dealer;
    private CardApi cardApi;
    private GameUtils gameUtils;
    private Deck deck;

    private Model() {
        this.player = new Player();
        this.dealer = new Dealer();
        this.cardApi = new FranceCardApi();
        this.gameUtils = new GameUtils();
        this.deck = cardApi.getDeck();
    }

    public Player getPlayer() {
        return player;
    }

    public Person getDealer() {
        return dealer;
    }

    public Deck getDeck() {
        return deck;
    }

    public CardApi getCardApi() {
        return cardApi;
    }

    public static Model getInstance(){
        return model;
    }

    public GameUtils getGameUtils() {
        return gameUtils;
    }

    public void resetValues(){
       int oldPlayerFund = player.getFund();
       player = new Player();
       player.setFund(oldPlayerFund);
       dealer = new Dealer();
       if(deck.getDeckCards().size()<20){
           deck = cardApi.getDeck();
       }
    }
    public boolean isGameOver(){
        boolean pass1 = model.getGameUtils().isPlayerScorePass21(model.getPlayer().getCardsSumValues());
        if(model.getPlayer().getCardsSumValuesSplit()>0){
            boolean pass2 = model.getGameUtils().isPlayerScorePass21(model.getPlayer().getCardsSumValuesSplit());
            return pass1 && pass2;
        }
        else{
            return pass1;
        }
    }
    public boolean isSplitGameOver(){
        if(model.getPlayer().getCardsSumValuesSplit()>0){
            boolean pass1 = model.getGameUtils().isPlayerScorePass21(model.getPlayer().getCardsSumValues());
            boolean pass2 = model.getGameUtils().isPlayerScorePass21(model.getPlayer().getCardsSumValuesSplit());
            return !pass1 && pass2;
        }
        return false;
    }
    public Result[] getResult(){
        return gameUtils.calculateResult(player.getCardsSumValues(),
                player.getCardsSumValuesSplit(),
                dealer.getCardsSumValues());
    }
    public int getPrize(Result[] results){
        int prize = gameUtils.calculatePrize(player.getBet(),results);
        if(prize>0){
            model.getPlayer().addFund(prize);
        }
        return prize;
    }
}
