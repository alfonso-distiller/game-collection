package net.distiller.gamecollect.services;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.not;

import java.util.List;
import java.util.Optional;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.distiller.gamecollect.model.Collectible;
import net.distiller.gamecollect.model.Game;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameServiceIT {
	
	@Autowired
	GameService gameService;
	
	@Test
	public void findAllOk() throws Exception {
		List<Game> games = gameService.findAll();
		
		assertThat(games, not(equalTo(nullValue())));
		assertThat(games, IsCollectionWithSize.hasSize(greaterThanOrEqualTo(4)));
	}
	
	@Test
	public void findByPlatform3DS() throws Exception {
		List<Game> games = gameService.findByPlatform("3DS");
		
		assertThat(games, not(equalTo(nullValue())));
		assertThat(games, IsCollectionWithSize.hasSize(greaterThanOrEqualTo(2)));
	}
	
	@Test
	public void findByPlatformNintendoSwitch() throws Exception {
		List<Game> games = gameService.findByPlatform("Nintendo Switch");
		
		assertThat(games, not(equalTo(nullValue())));
		assertThat(games, IsCollectionWithSize.hasSize(greaterThanOrEqualTo(2)));
	}
	
	@Test
	public void findByPlatformPC() throws Exception {
		List<Game> games = gameService.findByPlatform("PC");
		
		assertThat(games, not(equalTo(nullValue())));
		assertThat(games, IsCollectionWithSize.hasSize(greaterThanOrEqualTo(1)));
	}
	
	@Test
	public void findByPlatformEmpty() throws Exception {
		List<Game> games = gameService.findByPlatform("");
		
		assertThat(games, not(equalTo(nullValue())));
		assertThat(games, IsCollectionWithSize.hasSize(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void findByPlatformNull() throws Exception {
		List<Game> games = gameService.findByPlatform(null);
		
		assertThat(games, not(equalTo(nullValue())));
		assertThat(games, IsCollectionWithSize.hasSize(greaterThanOrEqualTo(0)));
	}
	
	@Test
	public void findByIdOk() throws Exception {
		Optional<Game> game = gameService.findById(1l);
		
		assertThat(game.isPresent(), equalTo(true));
		assertThat(game.get(), hasProperty("title",equalTo("Fire Emblem: Awakening")));
		assertThat(game.get(), hasProperty("platform", hasProperty("platform", equalTo("3DS"))));
	}
	
	@Test
	public void findByIdNotFound() throws Exception {
		Optional<Game> game = gameService.findById(-1l);
		
		assertThat(game.isPresent(), equalTo(false));
	}
	
	@Test
	public void save() throws Exception {
		Game game = gameService.save(new Game.Builder("Pokemon Y", "3DS")
				.addCollectible(Collectible.collected("MewTwo"))
				.addCollectible(Collectible.notCollected("Legendario1"))
				.addCollectible(Collectible.notCollected("Legendario2"))
				.build());
		
		assertThat(game, not(equalTo(nullValue())));
		assertThat(game, hasProperty("id", not(equalTo(nullValue()))));
		assertThat(game, hasProperty("platform", hasProperty("platform", equalTo("3DS"))));
		assertThat(game, hasProperty("collectibles", IsCollectionWithSize.hasSize(3)));
		assertThat(game, hasProperty("collectibles", hasItems(
					Collectible.collected("MewTwo"), 
					Collectible.notCollected("Legendario1"),
					Collectible.notCollected("Legendario2")
				)));
	}
}
