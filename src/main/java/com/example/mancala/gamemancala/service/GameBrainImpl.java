package com.example.mancala.gamemancala.service;

import com.example.mancala.gamemancala.entity.GameStatus;
import com.example.mancala.gamemancala.entity.MancalaGameEntity;
import com.example.mancala.gamemancala.util.Constants;
import com.example.mancala.gamemancala.util.MancalaGameUtil;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by shantonav on 07/08/2018.
 */
@Component
public class GameBrainImpl implements  GameBrain{

    @Override
    public  Integer makeAMoveForPlayerAndGetTheNextPlayer(final Integer[] pits, final int pitid, final Integer currentPlayer){
        Integer nextPlayer = (currentPlayer == Constants.PLAYER_ONE ? Constants.PLAYER_TWO : Constants.PLAYER_ONE);
        int startIndex = pitid - 1 ; // Get the array index for the pitId
        int numberOfSeedsInThePit = pits[startIndex]; // Get the no of seeds in the current pit
        // From the next pit till the number of seeds in pitId
        pits[startIndex] = 0; // Take all the seeds from the Pit
        int roundedPitIndex = startIndex + 1; // As after pit index 14 the next pit should be 1.

        for (int counter = 0 ; counter < numberOfSeedsInThePit  ; counter++){
            boolean isPitKalahForOpponentPlayer =
                    MancalaGameUtil.isPitAKalahForPlayer(roundedPitIndex+1,nextPlayer);
            // Check if pitIndex is a Kalah and it belongs to the opponent player
            if (isPitKalahForOpponentPlayer){
                if (currentPlayer == Constants.PLAYER_ONE) {
                    roundedPitIndex = Constants.ZERO; // Resetting back the index to array Index 0
                }else{
                    roundedPitIndex++; // Else move forward to the next pit.
                }
                counter--; // Reducing the counter by 1 as we did not sow our seed there.
                continue;
            }
            // Determine the rounded pit index
            roundedPitIndex = roundedPitIndex % 14;
            /**
             * This condition states if
             *
             * a. the pit is empty
             * AND
             * B. it is the last pit to be sowed
             *
             * Then determine opponent player's pit id and take all stones from there +
             * take this stone of the current player and put it in the current players kalah.
             */
            if (
                    pits[roundedPitIndex].equals(Constants.ZERO)
                            && counter == (numberOfSeedsInThePit - 1)
                            && !MancalaGameUtil.isPitAKalahForPlayer(roundedPitIndex+1,currentPlayer) ){
                int opponetPitId = MancalaGameUtil.getOpponentPitId(roundedPitIndex);
                int seedsInOpponentsPit = pits[opponetPitId];
                pits[opponetPitId] = 0; // Empty opponets pit in this case
                pits[MancalaGameUtil.getKalahIndexOfCurrentPayer(currentPlayer)-1] +=
                        ( seedsInOpponentsPit +1 ); // Including this last seed

            }else if (
                    counter == (numberOfSeedsInThePit - 1)
                    && MancalaGameUtil.isPitAKalahForPlayer(roundedPitIndex+1,currentPlayer)){
                // If the pit is the current player's Kalah then
                // add the seed to it
                // and do not change the current player
                pits[roundedPitIndex] += 1;
                nextPlayer = currentPlayer;
            }else{
                pits[roundedPitIndex] += 1;
            }


            roundedPitIndex++;
        }

        return  nextPlayer;
    }

    @Override
    public void postMoveCheckWhetherGameIsOver(MancalaGameEntity game, Integer currentPlayer, Integer[] pits) {
        // After the move check if the all pits of current player are empty
        // Check if the game is over and move all remaining stones to player's kalah
        if (MancalaGameUtil.isPlayersPitEmpty(pits,currentPlayer)){
            MancalaGameUtil.moveAllStonesToPlayersKalah(
                    currentPlayer == Constants.PLAYER_ONE ? Constants.PLAYER_TWO : Constants.PLAYER_ONE,pits);
            // And then game is over
            // Generate the game status
            // Finally
            game.setPits(pits);
            game.setGameStatus(GameStatus.GAMEOVER);
            game.setWhoWon(MancalaGameUtil.whoWon(pits));

        }
    }
}
