package com.example.mancala.gamemancala.util;

import java.util.Random;

/**
 * Created by shantonav on 07/08/2018.
 */
public class MancalaGameUtil {
    private static final Random aRandonNumber = new Random();

    public static Integer nextGameId(){
        return MancalaGameUtil.aRandonNumber.nextInt(Integer.MAX_VALUE);
    }


    public static int getOpponentPitId(final int pitId){
        return (Constants.KALAH_FORPLAYER_TWO- (pitId +1) % 14) - 1;
    }

    public static boolean isPitIdForCurrentPlayer(final Integer pitId, final Integer currentPlayer){
        boolean pitIdBelongsToCurrentPlayer = false;
        if (currentPlayer == Constants.PLAYER_ONE){
            if ( pitId >= Constants.PIT_ID_START_INDEX && pitId < Constants.KALAH_FOR_PLAYER_ONE){
                pitIdBelongsToCurrentPlayer = true;
            }
        }else {
            if (pitId >= Constants.KALAH_FOR_PLAYER_ONE+1 && pitId < Constants.PIT_ID_END_INDEX){
                pitIdBelongsToCurrentPlayer = true;
            }
        }

        return  pitIdBelongsToCurrentPlayer;
    }

    public static boolean isPlayersPitEmpty(final Integer[] pits,final Integer currentPlayer){
        boolean isPlayersPitEmpty = false;
        if ( currentPlayer == 1){
            isPlayersPitEmpty =
                    checkIfAllPitsOfPlayerempty(pits,Constants.PIT_ID_START_INDEX-1,Constants.KALAH_FOR_PLAYER_ONE-1); // Excluding the Kalah pit
        }else{
            isPlayersPitEmpty = checkIfAllPitsOfPlayerempty(pits,Constants.KALAH_FOR_PLAYER_ONE,Constants.PIT_ID_END_INDEX-1); // Excluding the Kalah pit
        }
        return  isPlayersPitEmpty;
    }

    private static boolean checkIfAllPitsOfPlayerempty(final Integer[] pits,final int start, final int end){
        for (int index =start ; index <end ; index++){
            if (  !pits[index].equals(Integer.valueOf(0))){
                return false;
            }
        }
        return true;
    }

    public static void moveAllStonesToPlayersKalah(final Integer player, final Integer[] pits){
        int kalahIndex = Constants.KALAH_FOR_PLAYER_ONE;
        int startIndex = Constants.ZERO;

        if (player == Constants.PLAYER_TWO){
            kalahIndex = Constants.KALAH_FORPLAYER_TWO;
            startIndex = Constants.KALAH_FOR_PLAYER_ONE;

        }
        int endIndex = kalahIndex -1 ;

        for (int index = startIndex  ; index< endIndex ; index++) {
            pits[kalahIndex-1] += pits[index] ;
            pits[index] = 0; // As we have added all the seeds to the player's kalah
        }

    }

    public static Integer whoWon(final Integer[] pits){
        if (pits[Constants.KALAH_FOR_PLAYER_ONE-1] > pits[Constants.KALAH_FORPLAYER_TWO-1])
            return Constants.PLAYER_ONE;
        else return Constants.PLAYER_TWO;
    }



    public static boolean isPitAKalahForPlayer(int pitIndex, Integer aPlayer) {
        boolean isPitAKalahForTheCurrentPlayer = false;
        if (( pitIndex == Constants.KALAH_FORPLAYER_TWO
                && aPlayer == Constants.PLAYER_TWO)
         || (pitIndex == Constants.KALAH_FOR_PLAYER_ONE
                && aPlayer == Constants.PLAYER_ONE) ){
            isPitAKalahForTheCurrentPlayer = true;
        }
        return isPitAKalahForTheCurrentPlayer;
    }

    public static int getKalahIndexOfCurrentPayer(final Integer currentPlayer){
        if (currentPlayer == Constants.PLAYER_ONE)
            return Constants.KALAH_FOR_PLAYER_ONE;
        else
            return Constants.KALAH_FORPLAYER_TWO;
    }


}
