package blackJackFX.model.game;

public class GameUtils {

    public int calculateResult(int playerScore,int dealerScore){
        //1 is win
        //0 is push
        //-1 is bust
        final int gameValue = 21;

        if(playerScore==gameValue){
            return 2;
        }
        if(playerScore<=gameValue && dealerScore<=gameValue){
            return Integer.compare(playerScore,dealerScore);
        }
        else if(playerScore<=gameValue && dealerScore>gameValue){
            return 1;
        }
        return -1;
    }
    public int[] calculateResult(int playerScore, int playerScore2, int dealerScore){
        int[] results;
        if(playerScore2>0){
            results = new int[2];
            results[0] = calculateResult(playerScore,dealerScore);
            results[1] = calculateResult(playerScore2,dealerScore);
        }else{
            results = new int[1];
            results[0] = calculateResult(playerScore,dealerScore);
        }
        return results;
    }
    public int calculatePrize(int bet,int result){
           return switch (result){
                case 1 -> bet*2;
                case 0 -> bet;
                case 2 -> (int)(bet*1.5);
                default -> -bet;
            };
     }
    public int calculatePrize(int bet,int[] results){
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
    public String madeStringResult(int result){
        return switch (result){
              case 1 -> String.valueOf(Result.WON);
              case 0 -> String.valueOf(Result.PUSH);
              case 2 -> String.valueOf(Result.BLACKJACK);
              default -> String.valueOf(Result.LOST);
          };
    }
    public String madeStringResult(int[] result){
        if(result.length== 2){
            return madeStringResult(result[0])+" and "+madeStringResult(result[1]);
        }
        else if(result.length==1){
            return madeStringResult(result[0]);
        }
        throw new IllegalArgumentException("Wrong argument");

    }
}
