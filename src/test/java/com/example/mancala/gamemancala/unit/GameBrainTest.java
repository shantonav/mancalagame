package com.example.mancala.gamemancala.unit;

import com.example.mancala.gamemancala.entity.GameStatus;
import com.example.mancala.gamemancala.entity.MancalaGameEntity;
import com.example.mancala.gamemancala.service.GameBrain;
import com.example.mancala.gamemancala.service.GameBrainImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by shantonav on 09/08/2018.
 */
public class GameBrainTest {

    @Test
    public void testMancalaMoveForPlayer1From1stPit(){
        GameBrain gameBrain = new GameBrainImpl();
        MancalaGameEntity gameEntity = new MancalaGameEntity(1234);
        Integer nextPlayer = gameBrain.makeAMoveForPlayerAndGetTheNextPlayer(
                gameEntity.getPits(),1,1
        );

        /**
         * This move should produce this
         * {gameId=1234, pits=[0, 7, 7, 7, 7, 7, 1, 6, 6, 6, 6, 6, 6, 0], nextPlayer=1}
         */
        gameBrain.postMoveCheckWhetherGameIsOver(gameEntity,1,gameEntity.getPits());
        Assert.assertEquals(Integer.valueOf(1),nextPlayer);
        Assert.assertEquals(Integer.valueOf(0),gameEntity.getPits()[0]); // Status of the selected Pit
        Assert.assertEquals(Integer.valueOf(1),gameEntity.getPits()[6]); // Status of player 1's Kalah
        Assert.assertEquals(Integer.valueOf(7),gameEntity.getPits()[3]); // Status of player 1's random pit
        Assert.assertEquals(GameStatus.INPROGRESS,gameEntity.getGameStatus());
        Assert.assertNull(gameEntity.getWhoWon());
    }

    // Scenario when player 1's seeds from a chosen pit crossed over opponents Kalah and lands on its own non-empty pit.
    @Test
    public void testMancalaMoveForPlayer1From4thPitWith12Seeds(){
        GameBrain gameBrain = new GameBrainImpl();
        MancalaGameEntity gameEntity = new MancalaGameEntity(1234);
        gameEntity.getPits()[3] = 12 ; // Let's assume 4th pit of player 1 has 12 seeds
        Integer nextPlayer = gameBrain.makeAMoveForPlayerAndGetTheNextPlayer(
                gameEntity.getPits(),4,1
        );
        gameBrain.postMoveCheckWhetherGameIsOver(gameEntity,1,gameEntity.getPits());

        /**
         * This move should produce this
         * {gameId=1234, pits=[7, 7, 7, 0, 7, 7, 1, 7, 7, 7, 7, 7, 7, 0], nextPlayer=2}
         */
        Assert.assertEquals(Integer.valueOf(2),nextPlayer);
        Assert.assertEquals(Integer.valueOf(0),gameEntity.getPits()[3]); // Status of the selected Pit
        Assert.assertEquals(Integer.valueOf(1),gameEntity.getPits()[6]); // Status of player 1's Kalah
        Assert.assertEquals(Integer.valueOf(7),gameEntity.getPits()[5]); // Status of player 1's random pit
        Assert.assertEquals(Integer.valueOf(0),gameEntity.getPits()[13]); // Status of player 2's Kalah
        Assert.assertEquals(Integer.valueOf(7),gameEntity.getPits()[0]); // Status of player 1's 1st pit
        Assert.assertEquals(Integer.valueOf(7),gameEntity.getPits()[1]); // Status of player 1's 2nd pit
        Assert.assertEquals(Integer.valueOf(7),gameEntity.getPits()[2]); // Status of player 1's 3rd pit
        Assert.assertEquals(GameStatus.INPROGRESS,gameEntity.getGameStatus());
        Assert.assertNull(gameEntity.getWhoWon());
    }

    // Scenario Player collected opponent player's pit's seed as its last seed landed on its empty pit.
    @Test
    public void testMancalaMoveForPlayer1From4thPitWith12SeedsAnd3rdPitBeingEmpty(){
        GameBrain gameBrain = new GameBrainImpl();
        MancalaGameEntity gameEntity = new MancalaGameEntity(1234);
        gameEntity.getPits()[3] = 12 ; // Let's assume 4th pit of player 1 has 12 seeds
        gameEntity.getPits()[2] = 0 ; // Assuming 3rd pit of player 1 is empty.
        Integer nextPlayer = gameBrain.makeAMoveForPlayerAndGetTheNextPlayer(
                gameEntity.getPits(),4,1
        );
        gameBrain.postMoveCheckWhetherGameIsOver(gameEntity,1,gameEntity.getPits());

        /**
         * This move should produce this
         * {gameId=1234, pits=[7, 7, 0, 0, 7, 7, 9, 7, 7, 7, 0, 7, 7, 0], nextPlayer=2}
         */
        Assert.assertEquals(Integer.valueOf(2),nextPlayer);
        Assert.assertEquals(Integer.valueOf(0),gameEntity.getPits()[3]); // Status of the selected Pit
        Assert.assertEquals(Integer.valueOf(9),gameEntity.getPits()[6]); // Status of player 1's Kalah as it should capture opponents seeds
        // (7)from 11th pit and add the last seed to its Kalah (1 +1) = 1 + 1 + 7 = 9
        Assert.assertEquals(Integer.valueOf(7),gameEntity.getPits()[5]); // Status of player 1's random pit
        Assert.assertEquals(Integer.valueOf(0),gameEntity.getPits()[13]); // Status of player 2's Kalah
        Assert.assertEquals(Integer.valueOf(7),gameEntity.getPits()[0]); // Status of player 1's 1st pit
        Assert.assertEquals(Integer.valueOf(7),gameEntity.getPits()[1]); // Status of player 1's 2nd pit
        Assert.assertEquals(Integer.valueOf(0),gameEntity.getPits()[2]); // Status of player 1's 3rd pit
        Assert.assertEquals(GameStatus.INPROGRESS,gameEntity.getGameStatus());
        Assert.assertNull(gameEntity.getWhoWon());
    }

    // Scenario Game over and player 2 won.
    @Test
    public void testMancalaMoveForPlayer1From6thPitWith4SeedsAndAllOtherPitsBeingEmpty(){
        GameBrain gameBrain = new GameBrainImpl();
        MancalaGameEntity gameEntity = new MancalaGameEntity(1234);

        gameEntity.getPits()[0] = 0 ;
        gameEntity.getPits()[1] = 0 ;
        gameEntity.getPits()[2] = 0 ;
        gameEntity.getPits()[3] = 0 ;
        gameEntity.getPits()[4] = 0 ;
        gameEntity.getPits()[5] = 4 ; // Let's assume 6th pit of player 1 has 4 seeds
        // Assuming all other pits of player 1 are empty.
        Integer nextPlayer = gameBrain.makeAMoveForPlayerAndGetTheNextPlayer(
                gameEntity.getPits(),6,1
        );
        gameBrain.postMoveCheckWhetherGameIsOver(gameEntity,1,gameEntity.getPits());

        /**
         * This move should produce this
         * {gameId=1234, pits=[7, 7, 7, 0, 7, 7, 1, 7, 7, 7, 7, 7, 7, 0], nextPlayer=2}
         */
        Assert.assertEquals(Integer.valueOf(2),nextPlayer);
        Assert.assertEquals(Integer.valueOf(0),gameEntity.getPits()[5]); // Status of the selected Pit
        Assert.assertEquals(Integer.valueOf(1),gameEntity.getPits()[6]); // Status of player 1's Kalah
        Assert.assertEquals(Integer.valueOf(39),gameEntity.getPits()[13]); // Status of player 2's Kalah

        for (int index=0 ; index < gameEntity.getPits().length ; index++){
            if (index == 6 || index == 13){
                continue;
            }
            Assert.assertEquals(Integer.valueOf(0),gameEntity.getPits()[index]);
        }
        Assert.assertEquals(GameStatus.GAMEOVER,gameEntity.getGameStatus());
        Assert.assertEquals(Integer.valueOf(2),gameEntity.getWhoWon());
    }


}
