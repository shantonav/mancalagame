package com.example.mancala.gamemancala.service;

import com.example.mancala.gamemancala.entity.MancalaGameEntity;
import com.example.mancala.gamemancala.model.MancalaGameStatus;
import com.example.mancala.gamemancala.repository.GameRepository;
import com.example.mancala.gamemancala.util.Constants;
import com.example.mancala.gamemancala.util.MancalaGameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created by shantonav on 07/08/2018.
 */
@Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.SERIALIZABLE)
public class MancalaGameServiceImpl implements  MancalaGameService{

    private static final Logger log = LoggerFactory.getLogger(MancalaGameServiceImpl.class);

    @Autowired
    private GameRepository gameRepository;

    @Override
    public Integer createANewGame() {
        MancalaGameEntity aNewGame = new MancalaGameEntity(MancalaGameUtil.aRandonNumber.nextInt(Integer.MAX_VALUE));
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

            if (pitId >= Constants.PIT_ID_START_INDEX && pitId <=pits.length){
                // Check if pit Id belongs to the current player
                // Check if pit with pitId is not empty


                if ( !MancalaGameUtil.isPitIdForCurrentPlayer(pitId,currentPlayer) ){
                    throw new GameServiceException("Pit id "+pitId+" is not valid for player "+currentPlayer);
                }else if ( pits[pitId].equals(Constants.ZERO)){
                    throw new GameServiceException("Pit id "+pitId+" is has no stones please chose another non-empty pit");
                }


                /**
                 * Only if the above two conditions are met then
                 * a. Make a move for the player.
                 * b. determine the next player.
                 */


                // After the move check if the pit is empty
                // Check if the game is over and move all remaining stones to player's kalah
                if (MancalaGameUtil.isPlayersPitEmpty(pits,currentPlayer)){
                    MancalaGameUtil.moveALlStonesToPlayersKalah(
                            currentPlayer == Constants.PLAYER_ONE ? Constants.PLAYER_TWO : Constants.PLAYER_ONE,pits);
                    // And then game is over
                    // Generate the game status
                    // Finally
                    gameRepository.delete(game);
                }

            }else{
                throw new GameServiceException("Pit id "+pitId+" is not valid and must be in range 1 and 14");
            }

        }else{
            throw new GameServiceException("No game with id "+gameId+" was created before or its game was over, so cannot make any move");
        }
        return null;
    }


}
