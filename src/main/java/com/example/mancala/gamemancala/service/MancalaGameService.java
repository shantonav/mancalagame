package com.example.mancala.gamemancala.service;

import com.example.mancala.gamemancala.model.MancalaGameStatus;

/**
 * Created by shantonav on 07/08/2018.
 */
public interface MancalaGameService {
    /**
     * Returns the game id
     * @return
     */
    public Integer createANewGame();

    /**
     * Given a game ID and a pit ID system should make the next move
     * @param gameId
     * @param pitId
     * @return
     */
    public MancalaGameStatus makeAMove(Integer gameId, Integer pitId);


}
