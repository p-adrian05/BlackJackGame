package blackJackFX.model.game;

import blackJackFX.model.Model;

import java.util.LinkedList;
import java.util.List;

public class Player extends Person{

    private int fund;
    private int bet;
    private List<Card> splitCards1;
    private List<Card> splitCards2;
    private

    public Player() {
        fund = 0;
        bet = 0;
        cards = new LinkedList<>();
        splitCards1 = new LinkedList<>();
        splitCards2 = new LinkedList<>();
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

    public List<Card> getSplitCards1() {
        return splitCards1;
    }

    public List<Card> getSplitCards2() {
        return splitCards2;
    }

    public void addCardSplit1(Card card){
        splitCards1.add(card);
    }
    public void addCardSplit2(Card card){
        splitCards2.add(card);
    }
    public int getCardsSumValuesSplit1(){
        return Model.getInstance().getDeck().calcCardsSumValue(this.splitCards1);
    }
    public int getCardsSumValuesSplit2(){
        return Model.getInstance().getDeck().calcCardsSumValue(this.splitCards2);
    }
    public boolean enableSplitCards(){
        if(cards.size()==2){
            if(cards.get(0).getIntValue()==cards.get(1).getIntValue()){
                splitCards1.add(cards.get(0));
                splitCards2.add(cards.get(1));
                return true;
            }
        }
        return false;
    }
}
