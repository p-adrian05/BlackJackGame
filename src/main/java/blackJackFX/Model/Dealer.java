package blackJackFX.Model;


import lombok.Data;

import java.util.List;

@Data
public class Dealer {

    private int cardsSumValues;
    private List<Card> cards;

}
