package com.example.mancala.gamemancala.entity;

/**
 * Created by shantonav on 08/08/2018.
 */
public enum GameStatus {
    INPROGRESS("Game in progress"),
    GAMEOVER("Game over");

    private String gameStatusDescr;
    private GameStatus(final String gameStatusDescr){
        this.gameStatusDescr = gameStatusDescr;
    }

    public String getGameStatusDescr(){
        return  this.gameStatusDescr;
    }

    @Override
    public String toString() {
        return "GameStatus{" +
                "gameStatusDescr='" + gameStatusDescr + '\'' +
                '}';
    }
}
