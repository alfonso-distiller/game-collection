package net.distiller.gamecollect.services;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import net.distiller.gamecollect.model.Game;
import net.distiller.gamecollect.model.Platform;
import net.distiller.gamecollect.repositories.GameRepository;


public class GameServiceImplTest {
	
	@Mock
	GameRepository gameRepository;
	GameService gameService;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		gameService = new GameServiceImpl(gameRepository);
	}
	
	@Test
	public void findAllWithElements() throws Exception {
		List<Game> games = new ArrayList<>();
		
		games.add(Game.of("Fire Emblem: Awakening", Platform.of("3DS")));
		games.add(Game.of("The Legend of Zelda", Platform.of("Nintendo Switch")));
		games.add(Game.of("Diablo III", Platform.of("Nintendo Switch")));
		games.add(Game.of("Diablo III", Platform.of("PC")));
		
		when(gameRepository.findAll()).thenReturn(games);
		
		List<Game> mockedResult = gameService.findAll();
		
		assertThat(mockedResult, not(equalTo(nullValue())));
		assertThat(mockedResult, not(IsEmptyCollection.empty()));
		assertThat(mockedResult, IsCollectionWithSize.hasSize(4));
        verify(gameRepository, times(1)).findAll();
	}
	
	@Test
	public void findAllWithOutElements() throws Exception {
		List<Game> games = new ArrayList<>();
		when(gameRepository.findAll()).thenReturn(games);
		
		List<Game> mockedResult = gameService.findAll();
		
		assertThat(mockedResult, not(equalTo(nullValue())));
		assertThat(mockedResult, IsEmptyCollection.empty());
        verify(gameRepository, times(1)).findAll();
	}
	
	@Test
	public void findByPlatformWithElements() throws Exception {
		List<Game> games = new ArrayList<>();
		
		games.add(Game.of("Fire Emblem: Awakening", Platform.of("3DS")));
		games.add(Game.of("The Legend of Zelda", Platform.of("Nintendo Switch")));
		games.add(Game.of("Diablo III", Platform.of("Nintendo Switch")));
		games.add(Game.of("Diablo III", Platform.of("PC")));
		
		when(gameRepository.findByPlatform(any()))
			.thenReturn(
					games.stream()
						.filter(game -> game.getPlatform().equals(Platform.of("3DS")))
						.collect(Collectors.toList())
						);
		
		List<Game> mockedResult = gameService.findByPlatform("3DS");
		
		assertThat(mockedResult, not(equalTo(nullValue())));
		assertThat(mockedResult, not(IsEmptyCollection.empty()));
		assertThat(mockedResult, IsCollectionWithSize.hasSize(1));
        verify(gameRepository, times(1)).findByPlatform(any());
	}
	
	@Test
	public void findByPlatformWithOutElements() throws Exception {
		List<Game> games = new ArrayList<>();
		
		games.add(Game.of("Fire Emblem: Awakening", Platform.of("3DS")));
		games.add(Game.of("The Legend of Zelda", Platform.of("Nintendo Switch")));
		games.add(Game.of("Diablo III", Platform.of("Nintendo Switch")));
		games.add(Game.of("Diablo III", Platform.of("PC")));
		
		when(gameRepository.findByPlatform(any()))
			.thenReturn(
					games.stream()
						.filter(game -> game.getPlatform().equals(Platform.of("PS4")))
						.collect(Collectors.toList())
						);
		
		List<Game> mockedResult = gameService.findByPlatform("PS4");
		
		assertThat(mockedResult, not(equalTo(nullValue())));
		assertThat(mockedResult, IsEmptyCollection.empty());
		assertThat(mockedResult, IsCollectionWithSize.hasSize(0));
        verify(gameRepository, times(1)).findByPlatform(any());
	}
	
	@Test
	public void findByIdWithElement() throws Exception {
		List<Game> games = new ArrayList<>();
		
		games.add(Game.of("Fire Emblem: Awakening", Platform.of("3DS")));
		games.add(Game.of("The Legend of Zelda", Platform.of("Nintendo Switch")));
		games.add(Game.of("Diablo III", Platform.of("Nintendo Switch")));
		games.add(Game.of("Diablo III", Platform.of("PC")));
		
		when(gameRepository.findById(anyLong()))
			.thenReturn(games.stream().findFirst());
		
		Optional<Game> mockedResult = gameService.findById(1l);
		
		assertThat(mockedResult, not(equalTo(nullValue())));
		assertThat(mockedResult.isPresent(), equalTo(true));
        verify(gameRepository, times(1)).findById(any());
	}
	
	@Test
	public void findByIdWithOutElement() throws Exception {
		List<Game> games = new ArrayList<>();
		
		games.add(Game.of("Fire Emblem: Awakening", Platform.of("3DS")));
		games.add(Game.of("The Legend of Zelda", Platform.of("Nintendo Switch")));
		games.add(Game.of("Diablo III", Platform.of("Nintendo Switch")));
		games.add(Game.of("Diablo III", Platform.of("PC")));
		
		when(gameRepository.findById(anyLong()))
			.thenReturn(Optional.empty());
		
		Optional<Game> mockedResult = gameService.findById(1l);
		
		assertThat(mockedResult, not(equalTo(nullValue())));
		assertThat(mockedResult.isPresent(), equalTo(false));
        verify(gameRepository, times(1)).findById(any());
	}
	
	
	@Test
	public void saveOk() throws Exception {
		Game game = Game.of("Fire Emblem: Awakening", Platform.of("3DS"));
		game.setId(1l);
		
		when(gameRepository.save(any()))
			.thenReturn(game);
		
		Game mockedResult = gameService.save(game);
		
		assertThat(mockedResult, not(equalTo(nullValue())));
		assertThat(mockedResult, hasProperty("id", equalTo(1l)));
		assertThat(mockedResult, hasProperty("title", equalTo("Fire Emblem: Awakening")));
		assertThat(mockedResult, hasProperty("platform", hasProperty("platform", equalTo("3DS"))));
		assertThat(mockedResult, hasProperty("collectibles", IsCollectionWithSize.hasSize(0)));
        verify(gameRepository, times(1)).save(any());
	}
}
