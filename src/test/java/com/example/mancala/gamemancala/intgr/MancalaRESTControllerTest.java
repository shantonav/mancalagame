package com.example.mancala.gamemancala.intgr;

import com.example.mancala.gamemancala.GameMancalaApplication;
import com.example.mancala.gamemancala.entity.GameStatus;
import com.example.mancala.gamemancala.model.ApiError;
import com.example.mancala.gamemancala.model.MancalaGame;
import com.example.mancala.gamemancala.model.MancalaGameStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;


@RunWith(TestOrderRunner.class)
@SpringBootTest(classes = {GameMancalaApplication.class})
public class MancalaRESTControllerTest {
    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(this.restDocumentation))
                .alwaysDo(MockMvcRestDocumentation.document("{method-name}",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint())))
                .build();
    }
    @Test
    @Order(order = 1)
    public void testGameCreation() throws Exception {
        createAndValidateAGameAndGetTheGameId();


    }

    @Test
    @Order(order = 2)
    public void testMoveOnAGameThatDoesNotExist() throws Exception {
        MvcResult result = this.mockMvc.perform(RestDocumentationRequestBuilders.put("/games/999/pits/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcRestDocumentation.document("game-creation",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()))
                ).andReturn();
        String jsonResponseString = result.getResponse().getContentAsString();

        ApiError apiError = this.objectMapper.readValue(jsonResponseString, ApiError.class);
        Assert.assertNotNull(apiError);
        Assert.assertEquals(Integer.valueOf(223000),apiError.getErrorCode());
        Assert.assertEquals("Input is not correct",apiError.getSystemErrorMessage());
        Assert.assertEquals("No game with id 999 was created before or its game was over, so cannot make any move",apiError.getRootCause());



    }

    @Test
    @Order(order = 3)
    public void testMoveOnAGameThatDoesExistButPitIdDoesNotBelongToTheCurrentPlayer() throws Exception {

        String gameId = createAndValidateAGameAndGetTheGameId().getGameId().toString();


        MvcResult result = this.mockMvc.perform(RestDocumentationRequestBuilders.put("/games/"+gameId+"/pits/10").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcRestDocumentation.document("game-creation",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()))
                ).andReturn();
        String jsonResponseString = result.getResponse().getContentAsString();

        ApiError apiError = this.objectMapper.readValue(jsonResponseString, ApiError.class);
        Assert.assertNotNull(apiError);
        Assert.assertEquals(Integer.valueOf(223000),apiError.getErrorCode());
        Assert.assertEquals("Input is not correct",apiError.getSystemErrorMessage());
        Assert.assertEquals("Pit id 10 is not valid for player 1",apiError.getRootCause());



    }

    private MancalaGame createAndValidateAGameAndGetTheGameId() throws Exception {
        MvcResult result = this.mockMvc.perform(RestDocumentationRequestBuilders.post("/games").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(MockMvcRestDocumentation.document("game-creation",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()))
                ).andReturn();
        String jsonResponseString = result.getResponse().getContentAsString();
        MancalaGame game = this.objectMapper.readValue(jsonResponseString, MancalaGame.class);
        Assert.assertNotNull(game);
        Assert.assertTrue(game.getGameId() instanceof  Integer);
        URI uri = new URI(game.getGameUrl());
        String gameId = uri.getPath().split("/")[2];
        Assert.assertEquals(game.getGameId(),Integer.valueOf(gameId));
        return game;
    }

    @Test
    @Order(order = 4)
    public void testMoveOnAGameThatDoesExistButPitIdIsTheKalahOfTheCurrentPlayer() throws Exception {

        String gameId = createAndValidateAGameAndGetTheGameId().getGameId().toString();


        MvcResult result = this.mockMvc.perform(RestDocumentationRequestBuilders.put("/games/"+gameId+"/pits/7").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andDo(MockMvcRestDocumentation.document("game-creation",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()))
                ).andReturn();
        String jsonResponseString = result.getResponse().getContentAsString();

        ApiError apiError = this.objectMapper.readValue(jsonResponseString, ApiError.class);
        Assert.assertNotNull(apiError);
        Assert.assertEquals(Integer.valueOf(223000),apiError.getErrorCode());
        Assert.assertEquals("Input is not correct",apiError.getSystemErrorMessage());
        Assert.assertEquals("Pit id 7 to make a move cannot be a player's Kalah ",apiError.getRootCause());



    }

    @Test
    @Order(order = 5)
    public void testMoveOnAGameThatDoesExistAndTheNextMoveIsOfTheSamePlayer() throws Exception {

        MancalaGame game = createAndValidateAGameAndGetTheGameId();


        MvcResult result = this.mockMvc.perform(RestDocumentationRequestBuilders.put("/games/"+game.getGameId()+"/pits/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("game-creation",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()))
                ).andReturn();
        String jsonResponseString = result.getResponse().getContentAsString();

        MancalaGameStatus gameStatus = this.objectMapper.readValue(jsonResponseString, MancalaGameStatus.class);

        Assert.assertEquals(GameStatus.INPROGRESS.getGameStatusDescr(),gameStatus.getGameStatus());
        Assert.assertNull(gameStatus.getWhoWon());
        Assert.assertEquals(Integer.valueOf(1),gameStatus.getPitStatus().get(7));

        // Doing another move for player 1 to confirm that after the last move the current player is still player 1
        // And that player 1's Kalah now has two stones.

        result = this.mockMvc.perform(RestDocumentationRequestBuilders.put("/games/"+game.getGameId()+"/pits/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("game-creation",
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint()))
                ).andReturn();
        jsonResponseString = result.getResponse().getContentAsString();

        gameStatus = this.objectMapper.readValue(jsonResponseString, MancalaGameStatus.class);

        Assert.assertEquals(GameStatus.INPROGRESS.getGameStatusDescr(),gameStatus.getGameStatus());
        Assert.assertNull(gameStatus.getWhoWon());
        Assert.assertEquals(Integer.valueOf(2),gameStatus.getPitStatus().get(7));
        Assert.assertEquals(Integer.valueOf(0),gameStatus.getPitStatus().get(1));
        Assert.assertEquals(Integer.valueOf(0),gameStatus.getPitStatus().get(2));


    }

}
