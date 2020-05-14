package blackJack.model.game;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameUtilsTest {

    @Test
    void testCalculateResult() {
        GameUtils gu = new GameUtils();
        assertEquals(Result.WON,gu.calculateResult(18,23));
        assertEquals(Result.WON,gu.calculateResult(19,18));
        assertEquals(Result.LOST,gu.calculateResult(22,19));
        assertEquals(Result.LOST,gu.calculateResult(15,18));
        assertEquals(Result.LOST,gu.calculateResult(23,23));
        assertEquals(Result.LOST,gu.calculateResult(23,24));
        assertEquals(Result.PUSH,gu.calculateResult(21,21));
        assertEquals(Result.PUSH,gu.calculateResult(17,17));
        assertEquals(Result.BLACKJACK,gu.calculateResult(21,24));
        assertEquals(Result.BLACKJACK,gu.calculateResult(21,18));

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,() -> gu.calculateResult(-1,23));
        assertEquals("Wrong arguments, must be > 0", e.getMessage());
        e = assertThrows(IllegalArgumentException.class,() -> gu.calculateResult(14,-12));
        assertEquals("Wrong arguments, must be > 0", e.getMessage());
    }

    @Test
    void testCalculateResult2() {
        GameUtils gu = new GameUtils();
        assertArrayEquals(new Result[]{Result.WON,Result.WON},gu.calculateResult(18,17,23));
        assertArrayEquals(new Result[]{Result.WON,Result.WON},gu.calculateResult(16,19,23));
        assertArrayEquals(new Result[]{Result.WON,Result.WON},gu.calculateResult(19,20,17));
        assertArrayEquals(new Result[]{Result.WON,Result.LOST},gu.calculateResult(19,15,17));
        assertArrayEquals(new Result[]{Result.WON,Result.LOST},gu.calculateResult(19,23,22));
        assertArrayEquals(new Result[]{Result.WON,Result.LOST},gu.calculateResult(19,23,17));
        assertArrayEquals(new Result[]{Result.WON,Result.PUSH},gu.calculateResult(20,17,17));
        assertArrayEquals(new Result[]{Result.WON,Result.PUSH},gu.calculateResult(20,17,17));
        assertArrayEquals(new Result[]{Result.WON,Result.BLACKJACK},gu.calculateResult(20,21,17));

        assertArrayEquals(new Result[]{Result.LOST,Result.WON},gu.calculateResult(15,18,17));
        assertArrayEquals(new Result[]{Result.LOST,Result.WON},gu.calculateResult(23,18,17));
        assertArrayEquals(new Result[]{Result.LOST,Result.WON},gu.calculateResult(23,18,23));
        assertArrayEquals(new Result[]{Result.LOST,Result.LOST},gu.calculateResult(23,23,24));
        assertArrayEquals(new Result[]{Result.LOST,Result.LOST},gu.calculateResult(18,19,21));
        assertArrayEquals(new Result[]{Result.LOST,Result.PUSH},gu.calculateResult(23,20,20));
        assertArrayEquals(new Result[]{Result.LOST,Result.PUSH},gu.calculateResult(18,20,20));
        assertArrayEquals(new Result[]{Result.LOST,Result.BLACKJACK},gu.calculateResult(18,21,20));

        assertArrayEquals(new Result[]{Result.PUSH,Result.WON},gu.calculateResult(17,19,17));
        assertArrayEquals(new Result[]{Result.PUSH,Result.LOST},gu.calculateResult(17,23,17));
        assertArrayEquals(new Result[]{Result.PUSH,Result.LOST},gu.calculateResult(17,14,17));
        assertArrayEquals(new Result[]{Result.PUSH,Result.PUSH},gu.calculateResult(21,21,21));
        assertArrayEquals(new Result[]{Result.PUSH,Result.BLACKJACK},gu.calculateResult(19,21,19));

        assertArrayEquals(new Result[]{Result.BLACKJACK,Result.WON},gu.calculateResult(21,19,23));
        assertArrayEquals(new Result[]{Result.BLACKJACK,Result.WON},gu.calculateResult(21,19,18));
        assertArrayEquals(new Result[]{Result.BLACKJACK,Result.LOST},gu.calculateResult(21,23,18));
        assertArrayEquals(new Result[]{Result.BLACKJACK,Result.LOST},gu.calculateResult(21,16,18));
        assertArrayEquals(new Result[]{Result.BLACKJACK,Result.LOST},gu.calculateResult(21,24,24));
        assertArrayEquals(new Result[]{Result.BLACKJACK,Result.PUSH},gu.calculateResult(21,16,16));
        assertArrayEquals(new Result[]{Result.BLACKJACK,Result.BLACKJACK},gu.calculateResult(21,21,16));
        assertArrayEquals(new Result[]{Result.BLACKJACK,Result.BLACKJACK},gu.calculateResult(21,21,24));

        assertArrayEquals(new Result[]{Result.WON},gu.calculateResult(19,0,22));
        assertArrayEquals(new Result[]{Result.LOST},gu.calculateResult(23,0,22));
        assertArrayEquals(new Result[]{Result.BLACKJACK},gu.calculateResult(21,0,18));
        assertArrayEquals(new Result[]{Result.PUSH},gu.calculateResult(21,0,21));

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,() -> gu.calculateResult(21,-2,23));
        assertEquals("Wrong arguments, must be > 0", e.getMessage());
        e = assertThrows(IllegalArgumentException.class,() -> gu.calculateResult(14,12,-12));
        assertEquals("Wrong arguments, must be > 0", e.getMessage());

    }

    @Test
    void testCalculatePrize() {
        GameUtils gu = new GameUtils();
        assertEquals(20,gu.calculatePrize(10,Result.WON));
        assertEquals(-10,gu.calculatePrize(10,Result.LOST));
        assertEquals(10,gu.calculatePrize(10,Result.PUSH));
        assertEquals(25,gu.calculatePrize(10,Result.BLACKJACK));
    }

    @Test
    void testCalculatePrize2() {
        GameUtils gu = new GameUtils();
        assertEquals(50,gu.calculatePrize(10,new Result[]{Result.BLACKJACK,Result.BLACKJACK}));
        assertEquals(45,gu.calculatePrize(10,new Result[]{Result.BLACKJACK,Result.WON}));
        assertEquals(15,gu.calculatePrize(10,new Result[]{Result.BLACKJACK,Result.LOST}));
        assertEquals(35,gu.calculatePrize(10,new Result[]{Result.BLACKJACK,Result.PUSH}));
        assertEquals(0,gu.calculatePrize(10,new Result[]{Result.PUSH,Result.LOST}));
        assertEquals(10,gu.calculatePrize(10,new Result[]{Result.WON,Result.LOST}));
        assertEquals(20,gu.calculatePrize(10,new Result[]{Result.PUSH,Result.PUSH}));
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,() -> gu.calculatePrize(10,new Result[]{}));
        assertEquals("Results argument must contains 2 enum values.", e.getMessage());
    }

    @Test
    void testValidateBet() {

    }

    @Test
    void testConvertResult() {
    }

    @Test
    void testMadeStringResult() {
    }
}