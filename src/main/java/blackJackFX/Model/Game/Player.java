package blackJackFX.Model.Game;

import java.util.LinkedList;
import java.util.List;

public class Player extends Person{

    private int fund;
    private int bet;
    private List<Card> splitCards;

    public Player() {
        fund = 0;
        bet = 0;
        cards = new LinkedList<>();
        cardsSumValues = 0;
        splitCards = new LinkedList<>();
    }

    public int getFund() {
        return fund;
    }

    public void setFund(int fund) {
        this.fund = fund;
    }

    public int getBet() {
        return bet;
    }

    public void addBetFromFund(int bet) {
        this.bet += bet;
        this.fund -= bet;
    }
    public void addFund(int fund) {
        this.fund += bet;
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
