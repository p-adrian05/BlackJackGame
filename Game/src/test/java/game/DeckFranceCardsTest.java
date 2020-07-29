package game;

import com.company.blackJack.card.Card;
import com.company.blackJack.card.Deck;
import com.company.blackJack.card.DeckFranceCards;
import com.company.blackJack.card.FranceCard;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class deckTest {

    @Test
    void testCalcCardsSumValue() throws MalformedURLException {
        Card card = new FranceCard("5","5S","SPADES",null);
        Card card1 = new FranceCard("KING","KH","HEARTS",null);
        Card card2 = new FranceCard("8","8C","CLUBS",null);
        Card card3 = new FranceCard("ACE","AD","DIAMONDS",null);
        Deck deck = new DeckFranceCards();

        assertEquals(24,deck.calcCardsSumValue(List.of(card,card1,card2,card3)));
        assertEquals(23,deck.calcCardsSumValue(List.of(card,card1,card2)));
        assertEquals(14,deck.calcCardsSumValue(List.of(card,card2,card3)));
        assertEquals(21,deck.calcCardsSumValue(List.of(card1,card3)));
        assertEquals(16,deck.calcCardsSumValue(List.of(card,card3)));
        assertEquals(18,deck.calcCardsSumValue(List.of(card2,card1)));
        assertEquals(11,deck.calcCardsSumValue(List.of(card3)));
    }
}