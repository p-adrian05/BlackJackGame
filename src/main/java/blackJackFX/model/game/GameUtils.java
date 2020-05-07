package blackJackFX.model.game;

public class GameUtils {

    public Result calculateResult(int playerScore,int dealerScore){
        //1 is win
        //0 is push
        //-1 is bust
        final int gameValue = 21;

        if(playerScore==gameValue){
            return Result.BLACKJACK;
        }
        if(playerScore<=gameValue && dealerScore<=gameValue){
            return madeResult(Integer.compare(playerScore,dealerScore));
        }
        else if(playerScore<=gameValue && dealerScore>gameValue){
            return Result.WON;
        }
        return Result.LOST;
    }
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
    public int calculatePrize(int bet,Result result){
           return switch (result){
               case WON -> bet*2;
               case PUSH -> bet;
               case BLACKJACK -> (int)(bet*1.5);
               default -> -bet;
            };
     }
    public int calculatePrize(int bet,Result[] results){
        if(results.length == 2){
            return calculatePrize(bet,results[0]) + calculatePrize(bet,results[1]);
        }else if(results.length==1){
            return calculatePrize(bet,results[0]);
        }
        throw new IllegalArgumentException("Results must contain 2 int value");
    }

    public boolean validateBet(int bet,int funds){
        return funds - bet >= 0;
    }

    public boolean isPlayerScorePass21(int score){
        return score > 21;
    }
    public boolean isScorePass16(int score){
        return score > 16;
    }
    public boolean isBlackJack(int score){
        return score == 21;
    }
    public Result madeResult(int result){
        return switch (result){
              case 1 -> Result.WON;
              case 0 -> Result.PUSH;
              default -> Result.LOST;
          };
    }
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
