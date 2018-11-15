package net.distiller.gamecollect.repositories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.distiller.gamecollect.model.Collectible;
import net.distiller.gamecollect.model.Game;
import net.distiller.gamecollect.model.Platform;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class GameRepositoryTest {
	
	@Autowired
	GameRepository repo;
	
	@Test
	public void saveGameItem() throws Exception {
		repo.save(Game.of("Fire Emblem: Awakening", Platform.of("3DS")));
		List<Game> items = (List<Game>) repo.findAll();
		Optional<Game> opItem = items.stream().findFirst();
		
		assertThat(opItem.isPresent(), equalTo(true));
		
		Game item = opItem.get();
		
		assertThat(item, hasProperty("title", equalTo("Fire Emblem: Awakening")));
		assertThat(item, hasProperty("platform", hasProperty("platform", equalTo("3DS"))));
	}
	
	@Test
	public void updateGameItem() throws Exception {
		repo.save(Game.of("Fire Emblem: Awakening", Platform.of("3DS")));
		List<Game> items = (List<Game>) repo.findAll();
		Optional<Game> opItem = items.stream().findFirst();
		
		assertThat(opItem.isPresent(), equalTo(true));
		
		Game item = opItem.get();
		
		assertThat(item, hasProperty("title", equalTo("Fire Emblem: Awakening")));
		assertThat(item, hasProperty("platform", hasProperty("platform", equalTo("3DS"))));
		
		item.setTitle("\'Fire Emblem: Awakening\' with DLC");
		repo.save(item);
		opItem = repo.findById(item.getId());
		
		assertThat(opItem.isPresent(), equalTo(true));
		assertThat(item, hasProperty("title", equalTo("\'Fire Emblem: Awakening\' with DLC")));
		assertThat(item, hasProperty("platform", hasProperty("platform", equalTo("3DS"))));
	}
	
	@Test
	public void findByPlatform() throws Exception {
		repo.save(Game.of("Fire Emblem: Awakening", Platform.of("3DS")));
		repo.save(Game.of("Pokemon X", Platform.of("3DS")));
		repo.save(Game.of("The Legend of Zelda", Platform.of("Nintendo Switch")));
		repo.save(Game.of("Diablo III", Platform.of("Nintendo Switch")));
		
		List<Game> dsGames = repo.findByPlatform(Platform.of("3DS"));
		List<Game> nintendoSwitch = repo.findByPlatform(Platform.of("Nintendo Switch"));
		List<Game> pcGames = repo.findByPlatform(Platform.of("PC"));
		
		assertThat(pcGames, IsEmptyCollection.empty());
		assertThat(dsGames, not(IsEmptyCollection.empty()));
		assertThat(dsGames, IsCollectionWithSize.hasSize(2));
		assertThat(dsGames, everyItem(hasProperty("platform", equalTo(Platform.of("3DS")))));
		assertThat(nintendoSwitch, not(IsEmptyCollection.empty()));
		assertThat(nintendoSwitch, IsCollectionWithSize.hasSize(2));
		assertThat(nintendoSwitch, everyItem(hasProperty("platform", equalTo(Platform.of("Nintendo Switch")))));
	}
	
	@Test
	public void saveGameWithCollectibles() throws Exception {
		Game game = repo.save(new Game.Builder("Pokemon X", "3DS")
				.addCollectible(Collectible.collected("MewTwo"))
				.addCollectible(Collectible.notCollected("Legendario1"))
				.addCollectible(Collectible.notCollected("Legendario2"))
				.build());
		
		assertThat(game, not(equalTo(nullValue())));
		assertThat(game, hasProperty("id", not(equalTo(nullValue()))));
		assertThat(game, hasProperty("title", equalTo("Pokemon X")));
		assertThat(game, hasProperty("platform", hasProperty("platform", equalTo("3DS"))));
		assertThat(game, hasProperty("collectibles", IsCollectionWithSize.hasSize(3)));
		
		game.removeAllCollectibles();
		game = repo.save(game);
		
		assertThat(game, hasProperty("collectibles", IsCollectionWithSize.hasSize(0)));
		
		game.addCollectible(Collectible.collected("MewTwo"));
		game.addCollectible(Collectible.collected("MewThree"));
		game.addCollectible(Collectible.collected("MewFour"));
		game = repo.save(game);
		
		assertThat(game, hasProperty("collectibles", IsCollectionWithSize.hasSize(3)));
		
		game.removeCollectible(Collectible.collected("MewTwo"));
		game = repo.save(game);
		
		assertThat(game, hasProperty("collectibles", IsCollectionWithSize.hasSize(2)));
	}
}
