package com.company.blackJack.game;

/**
 * Interface provides calculation functions for the game.
 */
public interface GameUtils {
    /**
     * Creates the final result to the player.
     *
     * @param playerScore the final summary score of the player
     * @param dealerScore the final summary score of the dealer
     * @return a {@code Result} enum value which represents the result
     */
    Result calculateResult(int playerScore,int dealerScore);

    /**
     * Creates the final result to the player with two player scores.
     *
     * @param playerScore the first final summary score of the player
     * @param playerScore2 the second final summary score of the player
     * @param dealerScore the final summary score of the dealer
     * @return an array of {@link Result} enums, which contains maximum two values
     *  and minimum one
     */
     Result[] calculateResult(int playerScore, int playerScore2, int dealerScore);

    /**
     * Calculates the prize to the player.
     *
     * @param bet the bet from the player given
     * @param result an {@link Result} enum value which represents the result of the game
     * @return the int value of the prize
     */
     int calculatePrize(int bet,Result result);

    /**
     * Calculates the prizes to the player.
     *
     * @param bet the bet from the player given
     * @param results an array of {@link Result} enum values which represents the results of the game
     * @return an array of the prizes, the length equals to the given results array
     * @throws IllegalArgumentException if the given array contains 0 or more than 2 values
     */
     int[] calculatePrizes(int bet,Result[] results);
    /**
     * Returns a value which represents a profit
     * for specified prizes and the actual {@link PlayerImpl}'s bet.
     * @param prizes array of int values
     * @param bet the given {@link PlayerImpl}'s bet.
     * @return an int value which means a profit
     */
     int calcProfit(int[] prizes,int bet);

    /**
     * Calculates the fund value for specified prizes. Only positive prizes add to funds.
     *
     * @param prizes an array of int values
     * @param fund the fund to calculate
     * @return a calculated fund value
     */
     int calculateFund(int[] prizes,int fund);

    /**
     * Returns whether the bet is possible to given from the player.
     *
     * @param bet the actual bet form the player
     * @param funds the actual funds from the player
     * @return {@code true} if the player have enough funds to subtract the bet,
     * {@code false} otherwise
     */
     boolean validateBet(int bet,int funds);
    /**
     * Made a {@code String} object from an array of {@link Result} enum values.
     *
     * @param results an array of {@link Result} enum values which represents the results of the game
     * @return a {@code String} object
     * @throws IllegalArgumentException if the given array contains 0 or more than 2 values
     */
     String madeStringResult(Result[] results);
}
