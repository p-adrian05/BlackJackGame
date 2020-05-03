package blackJackFX.Model;

import blackJackFX.Model.Game.*;

import java.util.LinkedList;
import java.util.List;

public class Model {

    private static final Model model = new Model();

    private final Player player;
    private final Person dealer;
    private final CardApi cardApi;
    private final GameUtils gameUtils;
    private final Deck deck;

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
}
