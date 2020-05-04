package blackJackFX.Model.Game;


import java.util.LinkedList;

public class Dealer extends Person{
    public Dealer() {
        cards = new LinkedList<>();
        cardsSumValues = 0;
    }
}
