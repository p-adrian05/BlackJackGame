package blackJackFX.Model;

import blackJackFX.Model.Game.*;
import blackJackFX.controller.PrimaryController;

import java.util.LinkedList;
import java.util.List;

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
       player = new Player();
       dealer = new Dealer();
       deck = cardApi.getDeck();
    }




}
