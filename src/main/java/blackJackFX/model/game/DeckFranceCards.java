package blackJackFX.model.game;

import java.util.LinkedList;
import java.util.List;

/**
 * Class represents an implementation of {@code Deck} interface.
 */

public class DeckFranceCards implements Deck{

    /**
     * The list of {@code FranceCard} objects.
     */
    private LinkedList<FranceCard> cards;

    @Override
    public List<Card> getDeckCards() {
        return new LinkedList<>(this.cards);
    }

    @Override
    public Card getCard() {
        return this.cards.removeFirst();
    }

    /**
     * Calculates the summary value of a list of {@code Card} objects.
     *
     * @param cards the list of {@code Card} objects
     * @return a summary value
     */
    @Override
    public int calcCardsSumValue(List<Card> cards) {
        int sum = 0;
        for(int i = 0; i<cards.size();i++){
            if(cards.get(i).getValue().equals("ACE") && sum>10){
                sum++;
            }else{
                sum+=cards.get(i).getIntValue();
            }
        }
        return sum;
    }



}



