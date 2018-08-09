package com.example.mancala.gamemancala.controller;

/**
 * Created by shantonav on 08/08/2018.
 */
public class GameApiException extends RuntimeException {
    public GameApiException(String message) {
        super(message);
    }

    public GameApiException(Throwable cause) {
        super(cause);
    }
}
