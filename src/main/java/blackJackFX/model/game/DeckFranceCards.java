package blackJackFX.model.game;

import java.util.LinkedList;
import java.util.List;

/**
 * Class represents an implementation of {@link Deck} interface.
 */

public class DeckFranceCards implements Deck{

    /**
     * The list of {@link FranceCard} objects.
     */
    private LinkedList<FranceCard> cards;

    @Override
    public List<Card> getDeckCards() {
        return new LinkedList<>(this.cards);
    }

    /**
     * Returns a {@link Card} object from the {@link #cards} list and remove it.
     *
     * @return a {@link Card} object
     */
    @Override
    public Card getCard() {
        return this.cards.removeFirst();
    }

    /**
     * Calculates the summary value of a list of {@link Card} objects.
     *
     * @param cards the list of {@link Card} objects
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



