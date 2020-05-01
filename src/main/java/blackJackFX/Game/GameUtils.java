package blackJackFX.Game;

import java.util.List;

public class GameUtils {

    public static int calculateResult(int player, int dealer){
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

    public static boolean validateBet(int bet,int funds){
        if(funds-bet>=0){
            return true;
        }
        else if(funds==0){
            System.out.println("Add funds first!");
            return false;
        }
        else if(funds<0){
            System.out.println("Funds must be positive!");
            return false;
        }
        else{
            System.out.println("Don't have enough funds!");
            return false;
        }
    }
    public static int calcCardsSumValues(List<Card> cards) {
        int sum = 0;
        for(int i = 0; i<cards.size();i++){
            if(cards.get(i).getValue().equals("ACE") && sum>10){
                sum++;
            }else{
                sum+=cards.get(i).getIntValue();
            }
        }
        return sum;
    }

    public static boolean isNumeric(String string) {
        if (string == null) {
            return false;
        }
        try {
            int num = Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}
