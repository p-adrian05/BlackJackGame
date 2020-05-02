package blackJackFX.Model;

import blackJackFX.Model.Game.*;

public class Model {

    private static final Model model = new Model();

    private final Player player;
    private final Person dealer;
    private final CardApi cardApi;

    private Model() {
        this.player = new Player();
        this.dealer = new Dealer();
        this.cardApi = new FranceCardApi();
    }

    public Player getPlayer() {
        return player;
    }

    public Person getDealer() {
        return dealer;
    }

    public CardApi getCardApi() {
        return cardApi;
    }

    public static Model getInstance(){
        return model;
    }

}
