package com.example.mancala.gamemancala.service;

import com.example.mancala.gamemancala.entity.MancalaGameEntity;

/**
 * Created by shantonav on 09/08/2018.
 */
public interface GameBrain {

    /**
     * Computes the move and determines the next player.
     *
     * @param pits
     * @param pitid
     * @param currentPlayer
     * @return
     */
    Integer makeAMoveForPlayerAndGetTheNextPlayer(final Integer[] pits, final int pitid, final Integer currentPlayer);

    /**
     * After a move it is important to check if the game is over or not.
     * @param game
     * @param currentPlayer
     * @param pits
     */
    void postMoveCheckWhetherGameIsOver(MancalaGameEntity game, Integer currentPlayer, Integer[] pits) ;
}
