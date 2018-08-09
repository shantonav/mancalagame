package com.example.mancala.gamemancala.unit;

import com.example.mancala.gamemancala.entity.MancalaGameEntity;
import com.example.mancala.gamemancala.model.MancalaGame;
import com.example.mancala.gamemancala.model.MancalaGameStatus;
import com.example.mancala.gamemancala.repository.GameRepository;
import com.example.mancala.gamemancala.service.GameBrain;
import com.example.mancala.gamemancala.service.GameBrainImpl;
import com.example.mancala.gamemancala.service.MancalaGameService;
import com.example.mancala.gamemancala.service.MancalaGameServiceImpl;
import com.example.mancala.gamemancala.util.MancalaGameUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

/**
 * Created by shantonav on 07/08/2018.
 */
@RunWith(SpringRunner.class)
public class MancalaServiceImplTest {

    @Autowired
    private MancalaGameService mancalaGameService;

    @TestConfiguration
    static class MancalaGameServiceTestConfiguration{
        @Bean
        public MancalaGameService mancalaGameService(){
            return new MancalaGameServiceImpl();
        }

        @Bean GameBrain gameBrain(){ return new GameBrainImpl(); }



    }

    @MockBean
    private static GameRepository mockedRepo;

    @Autowired
    private static GameBrain gameBrain;

    private static Integer gameId = MancalaGameUtil.nextGameId();



    @Test
    public void testNewGameCreation(){
        Mockito.when(mockedRepo.save(Mockito.any(MancalaGameEntity.class))).thenReturn(new MancalaGameEntity(gameId));

        Integer returnedGameId = mancalaGameService.createANewGame();
        Assert.assertEquals(gameId,returnedGameId);

    }

    @Test
    public void testMancalaMoveForPlayer1From1stPit(){
        MancalaGameEntity gameEntity = new MancalaGameEntity(gameId);

        Mockito.when(mockedRepo.findById(Mockito.any(Integer.class))).thenReturn(
                Optional.of(gameEntity) );

        MancalaGameStatus gameStatus = mancalaGameService.makeAMove(gameId,1);

        System.out.print(gameStatus);

    }



}
