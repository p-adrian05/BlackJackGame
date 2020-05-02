package blackJackFX.Model;

import blackJackFX.Model.Game.*;

public class Model {

    private static Model model = new Model();

    private Player player;
    private Person dealer;
    private CardApi cardApi;

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
