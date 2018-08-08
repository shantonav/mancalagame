package com.example.mancala.gamemancala.model;

import java.util.Map;

/**
 * Created by shantonav on 07/08/2018.
 */
public class MancalaGameStatus {
    private Integer gameId;
    private Map<Integer,Integer> pitMap;

    public MancalaGameStatus(Integer gameId, Map<Integer, Integer> pitMap) {
        this.gameId = gameId;
        this.pitMap = pitMap;
    }

    public Integer getGameId() {
        return gameId;
    }

    public Map<Integer, Integer> getPitMap() {
        return pitMap;
    }

    @Override
    public String toString() {
        return "MancalaGameStatus{" +
                "gameId=" + gameId +
                ", pitMap=" + pitMap +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MancalaGameStatus)) return false;

        MancalaGameStatus that = (MancalaGameStatus) o;

        if (gameId != null ? !gameId.equals(that.gameId) : that.gameId != null) return false;
        return pitMap != null ? pitMap.equals(that.pitMap) : that.pitMap == null;

    }

    @Override
    public int hashCode() {
        int result = gameId != null ? gameId.hashCode() : 0;
        result = 31 * result + (pitMap != null ? pitMap.hashCode() : 0);
        return result;
    }
}
