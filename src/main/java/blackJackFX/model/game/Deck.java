package blackJackFX.model.game;

import java.util.List;

public interface Deck {

    List<Card> getDeckCards();
    Card getCard();
    /**
     * Calculates the summary value of a list of {@link Card} objects.
     *
     * @param cards the list of {@link Card} objects
     * @return a summary value
     */
    int calcCardsSumValue(List<Card> cards);
}
