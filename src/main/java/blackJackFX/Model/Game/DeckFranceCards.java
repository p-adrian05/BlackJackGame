package blackJackFX.Model.Game;

import java.util.LinkedList;
import java.util.List;

public class DeckFranceCards implements Deck{

    private List<FranceCard> cards;

    @Override
    public List<Card> getDeckCards() {
        return new LinkedList<>(this.cards);
    }

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



