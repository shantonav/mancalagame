package com.example.mancala.gamemancala.service;

import com.example.mancala.gamemancala.entity.GameStatus;
import com.example.mancala.gamemancala.entity.MancalaGameEntity;
import com.example.mancala.gamemancala.model.MancalaGameStatus;
import com.example.mancala.gamemancala.repository.GameRepository;
import com.example.mancala.gamemancala.util.Constants;
import com.example.mancala.gamemancala.util.MancalaGameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by shantonav on 07/08/2018.
 */
@Service
@Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
public class MancalaGameServiceImpl implements  MancalaGameService{

    private static final Logger log = LoggerFactory.getLogger(MancalaGameServiceImpl.class);

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameBrain gameBrain;

    @Override
    public Integer createANewGame() {
        MancalaGameEntity aNewGame = new MancalaGameEntity(MancalaGameUtil.nextGameId());
        aNewGame = gameRepository.save(aNewGame);
        return aNewGame.getGameId();
    }

    @Override
    public MancalaGameStatus makeAMove(Integer gameId, Integer pitId) {
        Optional<MancalaGameEntity> mancalaGameEntity = gameRepository.findById(gameId);
        MancalaGameEntity game = null;
        if (mancalaGameEntity.isPresent() ){
            game = mancalaGameEntity.get();
            Integer currentPlayer = game.getCurrentPlayer();
            Integer[] pits = game.getPits();

            if (game.getGameStatus().equals(GameStatus.GAMEOVER)){
                throw new GameServiceException("Game id "+gameId+ " is already over and won by "+game.getWhoWon()
                        +"so you cannot play it any more");
            }

            // Check if pit Id belongs to the current player
            // Check if pit with pitId is not empty

            if ( !MancalaGameUtil.isPitIdForCurrentPlayer(pitId,currentPlayer) ){
                throw new GameServiceException("Pit id "+pitId+" is not valid for player "+currentPlayer);
            }

            if ( pits[pitId-1].equals(Constants.ZERO)){
                throw new GameServiceException("Pit id "+pitId+" is has no stones please chose another non-empty pit");
            }

            /**
             * Only if the above two conditions are met then
             * a. Make a move for the player.
             * b. determine the next player.
             */

            Integer nextPlayer = gameBrain.makeAMoveForPlayerAndGetTheNextPlayer(pits,pitId,currentPlayer);



            gameBrain.postMoveCheckWhetherGameIsOver(game, currentPlayer, pits);
            game.setCurrentPlayer(nextPlayer);
            gameRepository.save(game);

        }else{
            throw new GameServiceException("No game with id "+gameId+" was created before or its game was over, so cannot make any move");
        }
        return convertGameToGameStatus(game);
    }



    private MancalaGameStatus convertGameToGameStatus(MancalaGameEntity game) {
        final MancalaGameStatus gameStatus = new MancalaGameStatus(game.getGameId(),generatePitMap(game.getPits()));
        gameStatus.setGameStatus(game.getGameStatus().getGameStatusDescr());
        if (game.getGameStatus().equals(GameStatus.GAMEOVER)){
            gameStatus.setWhoWon(game.getWhoWon());
        }
        return gameStatus;
    }

    private Map<Integer,Integer> generatePitMap(Integer[] pits) {
        final Map<Integer,Integer> pitMap = new HashMap<>();

        for (int index = 0 ; index < pits.length ; index++){
            pitMap.put(index+1,pits[index]);
        }

        return pitMap;
    }


}
