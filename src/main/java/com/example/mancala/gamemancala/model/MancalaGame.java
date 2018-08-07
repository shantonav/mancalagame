package com.example.mancala.gamemancala.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by shantonav on 07/08/2018.
 */
public class MancalaGame implements Serializable{
    private Integer gameId;
    private String gameUrl;

    public MancalaGame(Integer gameId, String gameUrl) {
        this.gameId = gameId;
        this.gameUrl = gameUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MancalaGame)) return false;

        MancalaGame that = (MancalaGame) o;

        if (gameId != null ? !gameId.equals(that.gameId) : that.gameId != null) return false;
        return gameUrl != null ? gameUrl.equals(that.gameUrl) : that.gameUrl == null;

    }

    @Override
    public int hashCode() {
        int result = gameId != null ? gameId.hashCode() : 0;
        result = 31 * result + (gameUrl != null ? gameUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MancalaGame{" +
                "gameId=" + gameId +
                ", gameUrl='" + gameUrl + '\'' +
                '}';
    }

    public Integer getGameId() {
        return gameId;
    }

    public String getGameUrl() {
        return gameUrl;
    }
}
