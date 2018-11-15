package net.distiller.gamecollect.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import net.distiller.gamecollect.model.Collectible;
import net.distiller.gamecollect.services.CollectibleService;

public class CollectibleControllerTest {
	private MockMvc mockMvc;

    @Mock
    private CollectibleService colService;

    @InjectMocks
    private CollectibleController colController;
    
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(colController)
                .build();
    }
    
    
    @Test
    public void findAll() throws Exception {
    	List<Collectible> collectibles = new ArrayList<>();
		
		collectibles.add(Collectible.collected("item1"));
		collectibles.add(Collectible.collected("item2"));
		collectibles.add(Collectible.notCollected("item3"));
		collectibles.add(Collectible.notCollected("item4"));
    	
    	when(colService.findByGameId(anyLong())).thenReturn(collectibles);
    	
    	mockMvc.perform(get("/games/1/collectibles"))
    		.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(4)));
    	
    	verify(colService, times(1)).findByGameId(anyLong());
        verifyNoMoreInteractions(colService);
    }
    
    @Test
    public void findAllEmpty() throws Exception {
    	when(colService.findByGameId(anyLong())).thenReturn(Collections.emptyList());
    	
    	mockMvc.perform(get("/games/1/collectibles"))
    		.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(0)));
    	
    	verify(colService, times(1)).findByGameId(anyLong());
        verifyNoMoreInteractions(colService);
    }
    
    
    @Test
    public void findAllWithFlag() throws Exception {
    	List<Collectible> collectibles = new ArrayList<>();
		
		collectibles.add(Collectible.collected("item1"));
		collectibles.add(Collectible.collected("item2"));
    	
    	when(colService.findByGameIdAndIsCollected(anyLong(), anyBoolean())).thenReturn(collectibles);
    	
    	mockMvc.perform(get("/games/1/collectibles/true"))
    		.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(2)));
    	
    	verify(colService, times(1)).findByGameIdAndIsCollected(anyLong(), anyBoolean());
        verifyNoMoreInteractions(colService);
    }
    
    @Test
    public void findAllWithFlagEmpty() throws Exception {
    	
    	when(colService.findByGameIdAndIsCollected(anyLong(), anyBoolean())).thenReturn(Collections.emptyList());
    	
    	mockMvc.perform(get("/games/1/collectibles/true"))
    		.andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(0)));
    	
    	verify(colService, times(1)).findByGameIdAndIsCollected(anyLong(), anyBoolean());
        verifyNoMoreInteractions(colService);
    }
    
}
