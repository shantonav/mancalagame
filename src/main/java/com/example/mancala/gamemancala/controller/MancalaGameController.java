package com.example.mancala.gamemancala.controller;

import com.example.mancala.gamemancala.model.MancalaGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Created by shantonav on 07/08/2018.
 */
@RestController
@RequestMapping(value = "/games", produces = "application/json", consumes = "application/json")
public class MancalaGameController {

    private static final Logger log = LoggerFactory.getLogger(MancalaGameController.class);
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MancalaGame> startAndInitializeMancalaGame()  {
        final HttpServletRequest httpRequest =
                ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        final Integer gameId = Integer.valueOf(999);

        final String returnURI = httpRequest.getProtocol().substring(
                            0,httpRequest.getProtocol().indexOf("/")).toLowerCase()
                + "://"
                +httpRequest.getHeader("host")
                + "/games/"
                + gameId;

        MancalaGame aNewMancalaGame = new MancalaGame(gameId,returnURI);
        log.info("Mancala with game id {} created",gameId);
        return  new ResponseEntity<MancalaGame>(aNewMancalaGame,HttpStatus.CREATED);
    }
}
