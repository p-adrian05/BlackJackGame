package blackJackFX.Model.Game;

import java.util.List;

public class Player extends Person{

    private int funds;
    private int bet;
    private List<Card> splitCards;

    public int getFunds() {
        return funds;
    }

    public void setFunds(int funds) {
        this.funds = funds;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public List<Card> getSplitCards() {
        return splitCards;
    }

    public void setSplitCards(List<Card> splitCards) {
        this.splitCards = splitCards;
    }

}
