package blackJack.model.game;

import blackJack.model.Model;

import java.util.LinkedList;
import java.util.List;

/**
 * Class representing a kind of {@link Person} object and add more functionality beyond it.
 */

public class Player extends Person{

    private int fund;
    private int bet;
    private final List<Card> splitCards;
    private int hands;

    /**
     * Creates a {@code Player} object, initialize attributes.
     */
    public Player() {
        fund = 0;
        bet = 0;
        cards = new LinkedList<>();
        splitCards = new LinkedList<>();
        hands = 1;
    }

    public void madeSecondHand(){
        this.hands = 2;
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

    /**
     * Increases the value of {@link #bet} attribute and extract it from the {@link #fund}.
     * @param bet the value to add and extract
     */
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
    private void addCardSplit(Card card){
        splitCards.add(card);
    }

    /**
     * Returns the summary value of the {@link #splitCards} list
     * calling the {@link Deck#calcCardsSumValue(List)} function.
     *
     * @return a summary value
     */
    public int getCardsSumValuesSplit(){
        return Model.getInstance().getDeck().calcCardsSumValue(this.splitCards);
    }

    /**
     * Returns whether it is possible to split cards.
     *
     * @return {@code true} if two cards has in the {@link #cards} list
     * and the elements' values equals, {@code false} otherwise
     */
    public boolean enableSplitCards(){
        if(cards.size()==2){
            return cards.get(0).getIntValue() == cards.get(1).getIntValue();
        }
        return false;
    }

    /**
     * Splitting the cards, remove a card form {@link #cards} lists
     * and add to {@link #splitCards} list.
     */
    public void madeSplitCards(){
        if(enableSplitCards()){
            splitCards.add(cards.remove(1));
        }
    }

    @Override
    public void addCard(Card card) {
        if(this.getCardsSumValues()>21 || this.hands==2){
                addCardSplit(card);
        }else{
            super.addCard(card);
        }
    }
}
