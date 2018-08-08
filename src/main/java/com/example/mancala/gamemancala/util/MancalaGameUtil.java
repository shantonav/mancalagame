package com.example.mancala.gamemancala.util;

import java.util.Random;

/**
 * Created by shantonav on 07/08/2018.
 */
public class MancalaGameUtil {
    public static final Random aRandonNumber = new Random();

    public static Integer determineTheCurrentPlayer(final int[] pits,
                                                    final Integer currentPlayer, final int lastPitSowed){
        return 0;
    }

    public static int getOpponentPitId(final Integer currentPlayer, final int pitId){
        return 0;
    }

    public static boolean isPitIdForCurrentPlayer(final Integer pitId, final Integer currentPlayer){
        boolean pitIdBelongsToCurrentPlayer = false;
        if (currentPlayer == Constants.PLAYER_ONE){
            if ( pitId >= Constants.PIT_ID_START_INDEX && pitId <= Constants.KALAH_FOR_PLAYER_ONE){
                pitIdBelongsToCurrentPlayer = true;
            }
        }else {
            if (pitId >= Constants.KALAH_FOR_PLAYER_ONE+1 && pitId <= Constants.PIT_ID_END_INDEX){
                pitIdBelongsToCurrentPlayer = true;
            }
        }

        return  pitIdBelongsToCurrentPlayer;
    }

    public static boolean isPlayersPitEmpty(final Integer[] pits,final Integer currentPlayer){
        boolean isPlayersPitEmpty = false;
        if ( currentPlayer == 1){
            isPlayersPitEmpty = checkIfAllPitsOfPlayerempty(pits,Constants.PIT_ID_START_INDEX-1,Constants.KALAH_FOR_PLAYER_ONE);
        }else{
            isPlayersPitEmpty = checkIfAllPitsOfPlayerempty(pits,Constants.KALAH_FOR_PLAYER_ONE,Constants.PIT_ID_END_INDEX);
        }
        return  isPlayersPitEmpty;
    }

    private static boolean checkIfAllPitsOfPlayerempty(final Integer[] pits,final int start, final int end){
        for (int index =start ; index <end ; index++){
            if ( !pits[index].equals(Integer.valueOf(0))){
                return false;
            }
        }
        return true;
    }

    public static void moveALlStonesToPlayersKalah(final Integer player, final Integer[] pits){
        int kalahIndex = Constants.KALAH_FOR_PLAYER_ONE;
        int startIndex = Constants.PIT_ID_START_INDEX;
        if (player == Constants.PLAYER_TWO){
            kalahIndex = Constants.KALAH_FORPLAYER_TWO;
            startIndex = Constants.KALAH_FOR_PLAYER_ONE;
        }

        for (int index = startIndex - 1 ; index< kalahIndex ; index++) {
            pits[kalahIndex-1] += pits[index] ;
        }

    }

    public static Integer whoWon(final Integer[] pits){
        if (pits[Constants.KALAH_FOR_PLAYER_ONE-1] > pits[Constants.KALAH_FORPLAYER_TWO-1])
            return Constants.PLAYER_ONE;
        else return Constants.PLAYER_TWO;
    }

    public static void makeAMoveForPlayer(final Integer[] pits, final int pitid, final Integer currentPlayer){

        int startIndex = pitid - 1 ; // Get the array index for the pitid
        int numberOfSeedsInThePit = pits[startIndex]; // Get the no of seeds in the current pit
        // From the next pit till the number of seeds in at pitId
        pits[startIndex] = 0; // Take all the seeds from the Pit
        for (int seedIndex = 0 ; seedIndex < numberOfSeedsInThePit ; seedIndex++){

        }
    }

}
