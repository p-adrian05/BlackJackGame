package blackJackFX.Model;

import java.util.Collections;
import java.util.List;

public interface Deck {

    List<Card> getDeckCards();
    String getDeck_id();

    default void shuffle(){
        Collections.shuffle(getDeckCards());
    }

    static int calcCardsSumValues(List<Card> cards) {
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
