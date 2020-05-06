package blackJackFX.model.game;

import java.util.Collections;
import java.util.List;

public interface Deck {

    List<Card> getDeckCards();
    Card getCard();
    int calcCardsSumValue(List<Card> cards);
    default void shuffle(){
        Collections.shuffle(getDeckCards());
    }

}
