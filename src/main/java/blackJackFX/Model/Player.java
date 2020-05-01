package blackJackFX.Model;

import java.util.List;
import lombok.*;

@Data
public class Player {

    private int funds;
    private int bet;
    private List<Card> splitCards;
    private int cardsSumValues;
    private List<Card> cards;
    
}
