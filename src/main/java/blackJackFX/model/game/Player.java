package blackJackFX.model.game;

import blackJackFX.model.Model;

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
        this.fund += fund;
    }
    public void setBet(int bet) {
        this.bet = bet;
    }

    public List<Card> getSplitCards() {
        return splitCards;
    }
    public void addCardSplit(Card card){
        splitCards.add(card);
    }
    public int getCardsSumValuesSplit(){
        return Model.getInstance().getDeck().calcCardsSumValue(this.splitCards);
    }
    public boolean enableSplitCards(){
        if(cards.size()==2){
          if(cards.get(0).getIntValue()==cards.get(1).getIntValue()){
                return true;
           }
        }
        return false;
    }
    public void madeSplitCards(){
        splitCards.add(cards.remove(1));

    }

    @Override
    public void addCard(Card card) {
        if(splitCards.size()>0 && getCardsSumValues()>16){
                addCardSplit(card);
        }else{
            super.addCard(card);
        }
    }
}
