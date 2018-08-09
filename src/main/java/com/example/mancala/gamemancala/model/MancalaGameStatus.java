package com.example.mancala.gamemancala.model;

import java.util.Map;

/**
 * Created by shantonav on 07/08/2018.
 */
public class MancalaGameStatus {
    private Integer gameId;
    private Map<Integer,Integer> pitStatus;
    private String gameStatus;
    private Integer whoWon;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(String gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Integer getWhoWon() {
        return whoWon;
    }

    public void setWhoWon(Integer whoWon) {
        this.whoWon = whoWon;
    }

    public MancalaGameStatus(Integer gameId, Map<Integer, Integer> pitStatus) {
        this.gameId = gameId;
        this.pitStatus = pitStatus;
    }

    public Integer getGameId() {
        return gameId;
    }

    public Map<Integer, Integer> getPitStatus() {
        return pitStatus;
    }

    @Override
    public String toString() {
        return "MancalaGameStatus{" +
                "gameId=" + gameId +
                ", pitStatus=" + pitStatus +
                ", gameStatus='" + gameStatus + '\'' +
                ", whoWon=" + whoWon +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MancalaGameStatus)) return false;

        MancalaGameStatus that = (MancalaGameStatus) o;

        if (gameId != null ? !gameId.equals(that.gameId) : that.gameId != null) return false;
        return pitStatus != null ? pitStatus.equals(that.pitStatus) : that.pitStatus == null;

    }

    @Override
    public int hashCode() {
        int result = gameId != null ? gameId.hashCode() : 0;
        result = 31 * result + (pitStatus != null ? pitStatus.hashCode() : 0);
        return result;
    }
}
