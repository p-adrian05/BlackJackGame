package blackJackFX.Model.Game;

import java.util.List;

public class GameUtils {

    public int calculateResult(int playerScore, int dealerScore){
        //1 is win
        //0 is push
        //-1 is bust
        final int gameValue = 21;

        if(playerScore<=gameValue && dealerScore<=gameValue){
            return Integer.compare(playerScore,dealerScore);
        }
        else if(playerScore<=gameValue && dealerScore>gameValue){
            return 1;
        }
        return -1;
    }

    public int calculatePrize(int bet,int result){
           return switch (result){
                case 1 -> bet*2;
                case 0 -> 0;
                case 2 -> (int)(bet*1.5);
                default -> bet;
            };
        }

    public boolean validateBet(int bet,int funds){
        return funds - bet >= 0;
    }

    public boolean isPlayerScorePass21(int score){
        return score > 21;
    }
    public boolean isDealerScorePass16(int score){
        return score > 16;
    }
}
