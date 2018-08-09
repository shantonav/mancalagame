package com.example.mancala.gamemancala.unit;

import com.example.mancala.gamemancala.service.GameServiceException;
import com.example.mancala.gamemancala.util.ValidationUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by shantonav on 09/08/2018.
 */
public class ValidationUtilTest {
    @Rule
    public ExpectedException rule = ExpectedException.none();

    @Test
    public void testValidatePitWithPitBeingOutOfRange(){
        rule.expect(GameServiceException.class);
        rule.expectMessage("Pit id 15 is not valid and must be in range 1 and 14");

        ValidationUtil.validatePit(Integer.valueOf(15));

    }

    @Test
    public void testValidatePitWithPitIdOfPlayerOne(){
        rule.expect(GameServiceException.class);
        rule.expectMessage("Pit id 7 to make a move cannot be a player's Kalah ");

        ValidationUtil.validatePit(Integer.valueOf(7));

    }
    @Test
    public void testValidatePitWithPitIdOfPlayerTwo(){
        rule.expect(GameServiceException.class);
        rule.expectMessage("Pit id 14 to make a move cannot be a player's Kalah ");

        ValidationUtil.validatePit(Integer.valueOf(14));

    }
}
