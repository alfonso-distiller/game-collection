package net.distiller.gamecollect.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.distiller.gamecollect.model.Game;
import net.distiller.gamecollect.model.Platform;
import net.distiller.gamecollect.services.GameService;

public class GameControllerTest {
	private MockMvc mockMvc;

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;
    
    
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(gameController)
                .build();
    }
    
    @Test
    public void findAll() throws Exception {
    	List<Game> games = Arrays.asList(
    			Game.of("Fire Emblem: Awakening", Platform.of("3DS")),
    			Game.of("The Legend of Zelda", Platform.of("Nintendo Switch")),
    			Game.of("Diablo III", Platform.of("Nintendo Switch")),
    			Game.of("Diablo III", Platform.of("PC"))
    		);
    	
    	when(gameService.findAll()).thenReturn(games);
    	
    	mockMvc.perform(get("/games"))
    		.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(4)));
    	
    	verify(gameService, times(1)).findAll();
        verifyNoMoreInteractions(gameService);
    }
    
    @Test
    public void findByOkPlatform() throws Exception {
    	List<Game> games = Arrays.asList(
    			Game.of("Fire Emblem: Awakening", Platform.of("3DS")),
    			Game.of("The Legend of Zelda", Platform.of("Nintendo Switch")),
    			Game.of("Diablo III", Platform.of("Nintendo Switch")),
    			Game.of("Diablo III", Platform.of("PC"))
    		);
    	
    	when(gameService.findByPlatform(anyString()))
		.thenReturn(
				games.stream()
					.filter(game -> game.getPlatform().equals(Platform.of("3DS")))
					.collect(Collectors.toList())
					);
    	
    	mockMvc.perform(get("/games/platform/3DS"))
			.andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(jsonPath("$", hasSize(1)));
	
		verify(gameService, times(1)).findByPlatform(anyString());
	    verifyNoMoreInteractions(gameService);
    }
    
    @Test
    public void findByPlatformNotFound() throws Exception {
    	
    	when(gameService.findByPlatform(anyString()))
			.thenReturn(Collections.emptyList());
    	
    	mockMvc.perform(get("/games/platform/3DS"))
			.andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(jsonPath("$", hasSize(0)));
	
		verify(gameService, times(1)).findByPlatform(anyString());
	    verifyNoMoreInteractions(gameService);
    }
    
    @Test
    public void findByIdOk() throws Exception {
    	Game leGame = Game.of("Diablo III", Platform.of("PC"));
    	leGame.setId(1l);
    	
    	Optional<Game> game = Optional.of(leGame);
    	
    	when(gameService.findById(anyLong()))
			.thenReturn(game);
    	
    	mockMvc.perform(get("/games/1"))
			.andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(jsonPath("$.id", is(1)));
	
		verify(gameService, times(1)).findById(anyLong());
	    verifyNoMoreInteractions(gameService);
    }
    
    @Test
    public void findByIdNotFound() throws Exception {
    	when(gameService.findById(anyLong()))
			.thenReturn(Optional.empty());
    	
    	mockMvc.perform(get("/games/1"))
			.andExpect(status().isNotFound());
	
		verify(gameService, times(1)).findById(anyLong());
	    verifyNoMoreInteractions(gameService);
    }
    
    @Test
    public void saveNewGame() throws Exception {
    	Game game = Game.of("Diablo III", Platform.of("PC"));
    	
    	when(gameService.save(any())).thenReturn(game);
    	
    	mockMvc.perform(post("/games").contentType(MediaType.APPLICATION_JSON).content(asJsonString(game)))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(jsonPath("$.title", is("Diablo III")));
    	
    	verify(gameService, times(1)).save(any());
	    verifyNoMoreInteractions(gameService);
    }
    
    @Test
    public void updateGameOK() throws Exception {
    	Game game = Game.of("Diablo III", Platform.of("PC"));
    	
    	when(gameService.findById(anyLong())).thenReturn(Optional.of(game));
    	when(gameService.save(any())).thenReturn(game);
    	
    	mockMvc.perform(post("/games/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(game)))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
	        .andExpect(jsonPath("$.title", is("Diablo III")));
    	
    	verify(gameService, times(1)).findById(anyLong());
    	verify(gameService, times(1)).save(any());
	    verifyNoMoreInteractions(gameService);
    }
    
    @Test
    public void updateGameNotFound() throws Exception {
    	Game game = Game.of("Diablo III", Platform.of("PC"));
    	
    	when(gameService.findById(anyLong())).thenReturn(Optional.empty());
    	
    	mockMvc.perform(post("/games/1").contentType(MediaType.APPLICATION_JSON).content(asJsonString(game)))
			.andExpect(status().isNotFound());
    	
    	verify(gameService, times(1)).findById(anyLong());
	    verifyNoMoreInteractions(gameService);
    }
    
    private String asJsonString(final Object obj) throws Exception {
    	return new ObjectMapper().writeValueAsString(obj);
    }
}
