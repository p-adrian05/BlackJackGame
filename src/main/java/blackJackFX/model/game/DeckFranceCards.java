package blackJackFX.model.game;

import java.util.LinkedList;
import java.util.List;

public class DeckFranceCards implements Deck{

    private LinkedList<FranceCard> cards;

    @Override
    public List<Card> getDeckCards() {
        return new LinkedList<>(this.cards);
    }

    @Override
    public Card getCard() {
        return this.cards.removeFirst();
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



