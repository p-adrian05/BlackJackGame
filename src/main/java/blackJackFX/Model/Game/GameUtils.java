package blackJackFX.Model.Game;

import java.util.List;

public class GameUtils {

    public int calculateResult(int player, int dealer){
        //1 is win
        //0 is push
        //-1 is bust
        final int gameValue = 21;

        if(player<=gameValue && dealer<=gameValue){
            return Integer.compare(player,dealer);
        }
        else if(player<=gameValue && dealer>gameValue){
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


}
