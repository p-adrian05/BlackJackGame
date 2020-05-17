package blackJack.model.game;

import blackJack.model.Model;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * Class representing a kind of {@link Person} object and add more functionality beyond it.
 */
@Data
public class Player extends Person{

    private int fund;

    @Setter(AccessLevel.NONE)
    private int bet;

    private final List<Card> splitCards;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
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
    public boolean isEnableSplitCards(){
//        if(cards.size()==2){
//            return cards.get(0).getIntValue() == cards.get(1).getIntValue();
//        }
        return true;
    }
    /**
     * Splitting the cards, remove a card form {@link #cards} lists
     * and add to {@link #splitCards} list.
     */
    public void madeSplitCards(){
        if(isEnableSplitCards()){
            splitCards.add(cards.remove(1));
        }
    }
    /**
     * Adds a {@link Card} object to a list.
     *
     * @param card {@link Card} object added to a list or to another
     *                         if card splitting enabled
     */
    @Override
    public void addCard(Card card) {
        if(this.hands==2){
                addCardSplit(card);
        }else{
            super.addCard(card);
        }
    }
}
