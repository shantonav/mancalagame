package com.example.mancala.gamemancala.unit;

import com.example.mancala.gamemancala.entity.MancalaGameEntity;
import com.example.mancala.gamemancala.util.MancalaGameUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

/**
 * Created by shantonav on 07/08/2018.
 */
public class MancalaInitializationTest {

    @Test
    public void testMancalaInitialization(){

        MancalaGameEntity mancalaGameEntity = new MancalaGameEntity(MancalaGameUtil.nextGameId());

        Integer[] pits = mancalaGameEntity.getPits();
        int index = 0;
        for(Integer aPit : pits){
            if (index == 6 || index == 13) {
                Assert.assertEquals(Integer.valueOf(0),aPit);
            }else {
                Assert.assertEquals(Integer.valueOf(6), aPit);
            }
            System.out.println("Pit :"+index+" value="+aPit);
            index++;
        }
        System.out.print("GameId:"+mancalaGameEntity.getGameId());

    }
}
