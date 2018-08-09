package com.example.mancala.gamemancala.util;

import com.example.mancala.gamemancala.service.GameServiceException;

/**
 * Created by shantonav on 08/08/2018.
 */
public class ValidationUtil {

    public static void validatePit(final Integer pitId){
        if (!(pitId >= Constants.PIT_ID_START_INDEX && pitId <= Constants.PIT_ID_END_INDEX) ){
            throw new GameServiceException("Pit id "+pitId+" is not valid and must be in range 1 and 14");
        }

        if (pitId.equals(Constants.KALAH_FOR_PLAYER_ONE) || pitId.equals(Constants.KALAH_FORPLAYER_TWO)){
            throw new GameServiceException("Pit id "+pitId+" to make a move cannot be a player's Kalah ");
        }
    }
}
