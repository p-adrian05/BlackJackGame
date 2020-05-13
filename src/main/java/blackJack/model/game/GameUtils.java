package blackJack.model.game;

/**
 * Class representing the business logic of the game.
 */

public class GameUtils {

    /**
     * Creates the final result to the player.
     *
     * @param playerScore the final summary score of the player
     * @param dealerScore the final summary score of the dealer
     * @return a {@code Result} enum value which represents the result
     */
    public Result calculateResult(int playerScore,int dealerScore){
        final int gameValue = 21;

        if(playerScore==gameValue){
            return Result.BLACKJACK;
        }
        if(playerScore<=gameValue && dealerScore<=gameValue){
            return convertResult(Integer.compare(playerScore,dealerScore));
        }
        else if(playerScore<=gameValue && dealerScore>gameValue){
            return Result.WON;
        }
        return Result.LOST;
    }

    /**
     * Creates the final result to the player with two player scores.
     *
     * @param playerScore the first final summary score of the player
     * @param playerScore2 the second final summary score of the player
     * @param dealerScore the final summary score of the dealer
     * @return an array of {@link Result} enums, which contains maximum two values
     *  and minimum one
     */
    public Result[] calculateResult(int playerScore, int playerScore2, int dealerScore){
        Result[] results;
        if(playerScore2>0){
            results = new Result[2];
            results[0] = calculateResult(playerScore,dealerScore);
            results[1] = calculateResult(playerScore2,dealerScore);
        }else{
            results = new Result[1];
            results[0] = calculateResult(playerScore,dealerScore);
        }
        return results;
    }

    /**
     * Calculates the prize to the player.
     *
     * @param bet the bet from the player given
     * @param result an {@link Result} enum value which represents the result of the game
     * @return the value of the prize
     */
    public int calculatePrize(int bet,Result result){
           return switch (result){
               case WON -> bet*2;
               case PUSH -> bet;
               case BLACKJACK -> (int)(bet*1.5);
               default -> -bet;
            };
     }

    /**
     * Calculates the prize to the player.
     *
     * @param bet the bet from the player given
     * @param results an array of {@link Result} enum values which represents the results of the game
     * @return the value of the prize
     * @throws IllegalArgumentException if the given array contains 0 or more than 2 values
     */
    public int calculatePrize(int bet,Result[] results){
        if(results.length == 2){
            return calculatePrize(bet,results[0]) + calculatePrize(bet,results[1]);
        }else if(results.length == 1){
            return calculatePrize(bet,results[0]);
        }
        throw new IllegalArgumentException("Results must contains 2 enum values.");
    }

    /**
     * Returns whether the bet is possible to given from the player.
     *
     * @param bet the actual bet form the player
     * @param funds the actual funds from the player
     * @return {@code true} if the player have enough funds to made the bet,
     * {@code false} otherwise
     */
    public boolean validateBet(int bet,int funds){
        return funds - bet >= 0;
    }

    public boolean isPlayerScorePass21(int score){
        return score > 21;
    }
    public boolean isScorePass16(int score){
        return score > 16;
    }
    /**
     * Return whether the score value is equals to 21.
     *
     * @param score the score to check
     * @return {@code true} if the given score is equals to 21,
     * {@code false} otherwise
     */
    public boolean isBlackJack(int score){
        return score == 21;
    }

    /**
     * Converts an {@code int} result to a {@link Result} enum value.
     *
     * @param result must be 1, 0 or -1
     * @return {@link Result} enum value
     * @throws IllegalArgumentException if the given int value not 1, 0 or -1
     */
    public Result convertResult(int result){
        return switch (result){
              case 1 -> Result.WON;
              case 0 -> Result.PUSH;
              case -1 -> Result.LOST;
              default -> throw new IllegalArgumentException("Wrong argument: " + result);
        };
    }

    /**
     * Made a {@code String} object from an array of {@link Result} enum values.
     *
     * @param result an array of {@link Result} enum values which represents the results of the game
     * @return a {@code String} object
     * @throws IllegalArgumentException if the given array contains 0 or more than 2 values
     */
    public String madeStringResult(Result[] result){
        if(result.length== 2){
            return result[0]+" and "+result[1];
        }
        else if(result.length==1){
            return String.valueOf(result[0]);
        }
        throw new IllegalArgumentException("Wrong argument");
    }
}