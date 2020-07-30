package com.company.blackJack.game;

import org.springframework.stereotype.Component;

/**
 * Class implementation of {@link GameUtils} interface
 */
@Component
public class GameUtilsImpl implements GameUtils {

    @Override
    public Result calculateResult(int playerScore,int dealerScore){
        final int gameValue = 21;

        if(playerScore<0 || dealerScore<0){
            throw new IllegalArgumentException("Wrong arguments, must be > 0");
        }else{
            if(playerScore==gameValue && dealerScore!=21){
                return Result.BLACKJACK;
            }
            else if(playerScore<=gameValue && dealerScore<=gameValue){
                return convertIntResult(Integer.compare(playerScore,dealerScore));
            }
            else if(playerScore <= gameValue){
                return Result.WON;
            }
            return Result.LOST;
        }
    }

    @Override
    public Result[] calculateResult(int playerScore, int playerScore2, int dealerScore) {
        Result[] results;
        if (playerScore < 0 || playerScore2 < 0 || dealerScore < 0) {
            throw new IllegalArgumentException("Wrong arguments, must be > 0");
        } else {
            if (playerScore2 > 0) {
                results = new Result[2];
                results[0] = calculateResult(playerScore, dealerScore);
                results[1] = calculateResult(playerScore2, dealerScore);
            } else {
                results = new Result[1];
                results[0] = calculateResult(playerScore, dealerScore);
            }
            return results;
        }
    }

    @Override
    public int calculatePrize(int bet,Result result){
        switch (result){
            case WON:
                return bet*2;
            case PUSH:
                return bet;
            case BLACKJACK:
                return (int)(bet*2.5);
            default:
                return -bet;
        }
     }

    @Override
    public int[] calculatePrizes(int bet,Result[] results){
        if(results.length == 2){
            bet = bet/2;
            return new int[]{calculatePrize(bet,results[0]),calculatePrize(bet,results[1])};
        }else if(results.length == 1){
            return new int[]{calculatePrize(bet,results[0])};
        }
        throw new IllegalArgumentException("Results argument must contains 2 enum values.");
    }

    @Override
    public int calcProfit(int[] prizes,int bet){
        int profit = 0;
        if(prizes.length==2){
            bet = bet/2;
        }
        for (int prize : prizes) {
            if (prize < 0) {
                profit += prize;
            } else {
                profit += prize - bet;
            }
        }
        return profit;
    }

    @Override
    public int calculateFund(int[] prizes,int fund){
        int prize1 = prizes[0];
        int prize2 = 0;
        if(prizes.length == 2){
            prize2 = prizes[1];
        }
        if(prize1>0){
            fund += prize1;
        }
        if(prize2>0){
            fund += prize2;
        }
        return fund;
    }

    @Override
    public boolean validateBet(int bet,int funds){
        return funds - bet >= 0;
    }

    public Result convertIntResult(int result){
        switch (result){
            case 1 :
                return Result.WON;
            case 0 :
                return Result.PUSH;
            case -1:
                return Result.LOST;
            default: throw new IllegalArgumentException("Wrong argument: " + result);
        }
    }

    @Override
    public String madeStringResult(Result[] results){
        if(results.length== 2){
            return results[0]+" and "+results[1];
        }
        else if(results.length==1){
            return String.valueOf(results[0]);
        }
        throw new IllegalArgumentException("Results argument must contains 2 or 1 enum values.");
    }
}
