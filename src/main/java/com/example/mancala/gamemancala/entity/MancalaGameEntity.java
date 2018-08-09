package com.example.mancala.gamemancala.entity;

import com.example.mancala.gamemancala.util.Constants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

/**
 * Created by shantonav on 07/08/2018.
 */
@Document
public class MancalaGameEntity implements Serializable{
    @Id
    private Integer gameId;
    private Integer[] pits ;
    private GameStatus gameStatus;
    private Integer whoWon;

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public void setWhoWon(Integer whoWon) {
        this.whoWon = whoWon;
    }

    public MancalaGameEntity(Integer gameId) {
        this.gameId = gameId;
        init();
    }

    private int currentPlayer = 1;

    private void init(){
        final int initialSeeds = 6;
        pits = Stream.generate(() -> initialSeeds)
                        .limit(14).toArray(pit -> new Integer[pit]);

        pits[pits.length/2-1] = Constants.ZERO; // Kalah for Player 1
        pits[pits.length-1] = Constants.ZERO ; // Kalah for player 2

        this.gameStatus = GameStatus.INPROGRESS;
    }

    public Integer getGameId() {
        return gameId;
    }

    public Integer[] getPits() {
        return pits;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    @Override
    public String toString() {
        return "MancalaGameEntity{" +
                "gameId=" + gameId +
                ", pits=" + Arrays.toString(pits) +
                ", currentPlayer=" + currentPlayer +
                '}';
    }

    public Integer getWhoWon() {
        return whoWon;
    }

    public GameStatus getGameStatus() {

        return gameStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof MancalaGameEntity)) return false;

        MancalaGameEntity that = (MancalaGameEntity) o;

        if (currentPlayer != that.currentPlayer) return false;
        if (gameId != null ? !gameId.equals(that.gameId) : that.gameId != null) return false;
        return Arrays.equals(pits, that.pits);

    }

    @Override
    public int hashCode() {
        int result = gameId != null ? gameId.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(pits);
        result = 31 * result + currentPlayer;
        return result;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setPits(Integer[] pits) {
        this.pits = pits;
    }
}
