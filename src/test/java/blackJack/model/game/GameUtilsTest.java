package blackJack.model.game;

import blackJack.model.game.GameUtils;
import blackJack.model.game.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameUtilsTest {

    @Test
    void testCalculateResult() {
        GameUtils gu = new GameUtils();
        Assertions.assertEquals(Result.WON,gu.calculateResult(18,23));
        assertEquals(Result.WON,gu.calculateResult(19,18));
        assertEquals(Result.LOST,gu.calculateResult(22,19));
        assertEquals(Result.LOST,gu.calculateResult(15,18));
        assertEquals(Result.LOST,gu.calculateResult(23,23));
        assertEquals(Result.LOST,gu.calculateResult(23,24));
        assertEquals(Result.PUSH,gu.calculateResult(21,21));
        assertEquals(Result.PUSH,gu.calculateResult(17,17));
        assertEquals(Result.BLACKJACK,gu.calculateResult(21,24));
        assertEquals(Result.BLACKJACK,gu.calculateResult(21,18));

        assertThrows(IllegalArgumentException.class,() -> gu.calculateResult(14,-12));
        assertThrows(IllegalArgumentException.class,() -> gu.calculateResult(-14,12));
        assertThrows(IllegalArgumentException.class,() -> gu.calculateResult(-14,-12));
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

        assertThrows(IllegalArgumentException.class,() -> gu.calculateResult(14,-17,15));
        assertThrows(IllegalArgumentException.class,() -> gu.calculateResult(-14,-17,-15));
        assertThrows(IllegalArgumentException.class,() -> gu.calculateResult(-14,17,15));
        assertThrows(IllegalArgumentException.class,() -> gu.calculateResult(14,-17,-15));
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
    void testCalculatePrizes() {
        GameUtils gu = new GameUtils();
        assertArrayEquals(new int[]{25,25},gu.calculatePrizes(20,new Result[]{Result.BLACKJACK,Result.BLACKJACK}));
        assertArrayEquals(new int[]{25,20},gu.calculatePrizes(20,new Result[]{Result.BLACKJACK,Result.WON}));
        assertArrayEquals(new int[]{25,-10},gu.calculatePrizes(20,new Result[]{Result.BLACKJACK,Result.LOST}));
        assertArrayEquals(new int[]{25,10},gu.calculatePrizes(20,new Result[]{Result.BLACKJACK,Result.PUSH}));
        assertArrayEquals(new int[]{10,-10},gu.calculatePrizes(20,new Result[]{Result.PUSH,Result.LOST}));
        assertArrayEquals(new int[]{20,-10},gu.calculatePrizes(20,new Result[]{Result.WON,Result.LOST}));
        assertArrayEquals(new int[]{10,10},gu.calculatePrizes(20,new Result[]{Result.PUSH,Result.PUSH}));
        assertArrayEquals(new int[]{40},gu.calculatePrizes(20,new Result[]{Result.WON}));
        assertArrayEquals(new int[]{-20},gu.calculatePrizes(20,new Result[]{Result.LOST}));

        assertThrows(IllegalArgumentException.class,() -> gu.calculatePrizes(10,new Result[]{}));
        assertThrows(IllegalArgumentException.class,() -> gu.calculatePrizes(20,new Result[]{Result.PUSH,Result.PUSH,Result.LOST}));
    }
    @Test
    void testCalcProfit() {
        GameUtils gu = new GameUtils();
        assertEquals(25,gu.calcProfit(new int[]{25,20},20));
        assertEquals(0,gu.calcProfit(new int[]{20,-10},20));
        assertEquals(0,gu.calcProfit(new int[]{10,10},20));
        assertEquals(-10,gu.calcProfit(new int[]{10,-10},20));
        assertEquals(20,gu.calcProfit(new int[]{40},20));
        assertEquals(-20,gu.calcProfit(new int[]{-20},20));
        assertEquals(0,gu.calcProfit(new int[]{20},20));
    }

    @Test
    void testValidateBet() {
        GameUtils gu = new GameUtils();
        assertTrue(gu.validateBet(10,100));
        assertTrue(gu.validateBet(100,100));
        assertFalse(gu.validateBet(105,100));
    }

    @Test
    void testConvertIntResult() {
        GameUtils gu = new GameUtils();
        assertEquals(Result.WON,gu.convertIntResult(1));
        assertEquals(Result.LOST,gu.convertIntResult(-1));
        assertEquals(Result.PUSH,gu.convertIntResult(0));
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,() -> gu.convertIntResult(10));
        assertEquals("Wrong argument: 10", e.getMessage());
    }
    @Test
    void testMadeStringResult() {
        GameUtils gu = new GameUtils();
        assertEquals("PUSH and LOST", gu.madeStringResult(new Result[]{Result.PUSH, Result.LOST}));
        assertEquals("BLACKJACK and WON", gu.madeStringResult(new Result[]{Result.BLACKJACK, Result.WON}));
        assertEquals("BLACKJACK", gu.madeStringResult(new Result[]{Result.BLACKJACK}));
        assertThrows(IllegalArgumentException.class,() -> gu.madeStringResult(new Result[]{}));
        assertThrows(IllegalArgumentException.class,() -> gu.madeStringResult(new Result[]{Result.PUSH,Result.WON,Result.LOST}));
    }

    @Test
    void calculateFund() {
        GameUtils gu = new GameUtils();
        assertEquals(110,gu.calculateFund(new int[]{10,-10},100));
        assertEquals(100,gu.calculateFund(new int[]{10,20},70));
        assertEquals(70,gu.calculateFund(new int[]{-10,-20},70));
        assertEquals(90,gu.calculateFund(new int[]{-15,20},70));
        assertEquals(70,gu.calculateFund(new int[]{-15},70));
        assertEquals(70,gu.calculateFund(new int[]{0},70));
        assertEquals(80,gu.calculateFund(new int[]{10},70));

    }
}