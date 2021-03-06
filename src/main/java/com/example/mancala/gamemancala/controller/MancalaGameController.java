package com.example.mancala.gamemancala.controller;

import com.example.mancala.gamemancala.model.MancalaGame;
import com.example.mancala.gamemancala.model.MancalaGameStatus;
import com.example.mancala.gamemancala.service.GameServiceException;
import com.example.mancala.gamemancala.service.MancalaGameService;
import com.example.mancala.gamemancala.util.MancalaGameUtil;
import com.example.mancala.gamemancala.util.ValidationUtil;
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
import java.util.Optional;

/**
 * Created by shantonav on 07/08/2018.
 */
@RestController
@RequestMapping(value = "/games", produces = "application/json", consumes = "application/json")
public class MancalaGameController {

    @Autowired
    private MancalaGameService mancalaGameService;

    private static final Logger log = LoggerFactory.getLogger(MancalaGameController.class);
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MancalaGame> startAndInitializeMancalaGame()  {
        final HttpServletRequest httpRequest =
                ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
        final Integer gameId = mancalaGameService.createANewGame();



        MancalaGame aNewMancalaGame = new MancalaGame(gameId,getReturnURI(httpRequest,Optional.of(gameId.toString())));
        log.info("Mancala with game id {} created",gameId);
        return  new ResponseEntity<>(aNewMancalaGame,HttpStatus.CREATED);
    }

    @PutMapping("{gameid}/pits/{pitid}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MancalaGameStatus> makeAMoveInTheGame(@PathVariable("gameid") Integer gameId,
                                                                @PathVariable("pitid") Integer pitId){
        final HttpServletRequest httpRequest =
                ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();



        MancalaGameStatus gameStatus = null;
        try {
            ValidationUtil.validatePit(pitId);
            gameStatus = mancalaGameService.makeAMove(gameId, pitId);
            gameStatus.setUrl(getReturnURI(httpRequest,Optional.of(gameId.toString())) );
        }catch(GameServiceException ex){
            throw new GameApiException(ex.getMessage());
        }
        return new ResponseEntity<>(gameStatus,HttpStatus.OK);
    }

    private String getReturnURI(final HttpServletRequest httpRequest, final Optional<String> additionalPath) {
        final String returnURI = httpRequest.getProtocol().substring(
                0,httpRequest.getProtocol().indexOf("/")).toLowerCase()
                + "://"
                +httpRequest.getHeader("host")
                + "/games/"
                + (additionalPath.isPresent() ?  additionalPath.get(): "") ;
        return returnURI;
    }
}
