package net.distiller.gamecollect.repositories;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import java.util.List;

import javax.transaction.Transactional;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.distiller.gamecollect.model.Collectible;
import net.distiller.gamecollect.model.Game;

@RunWith(SpringRunner.class)
@DataJpaTest
@Transactional
public class CollectibleRepositoryTest {
	
	@Autowired
	CollectibleRepository collRepo;
	
	@Autowired
	GameRepository gameRepo;
	
	@Test
	public void givenAnGameId_thenfindAllCollectibles_whenGameHasCollectibles() throws Exception {
		Game game = gameRepo.save(new Game.Builder("Pokemon X", "3DS")
				.addCollectible(Collectible.collected("MewTwo"))
				.addCollectible(Collectible.notCollected("Legendario1"))
				.addCollectible(Collectible.notCollected("Legendario2"))
				.build());
		
		List<Collectible> collectibles = collRepo.findByGameId(game.getId());
		
		assertThat(collectibles, not(equalTo(nullValue())));
		assertThat(collectibles, IsCollectionWithSize.hasSize(greaterThanOrEqualTo(3)));
	}
	
	@Test
	public void givenAnGameId_thenfindAllCollectibles_whenGameDoesntHaveCollectibles() throws Exception {
		Game game = gameRepo.save(new Game.Builder("Pokemon X", "3DS")
				.build());
		
		List<Collectible> collectibles = collRepo.findByGameId(game.getId());
		
		assertThat(collectibles, not(equalTo(nullValue())));
		assertThat(collectibles, IsCollectionWithSize.hasSize(equalTo(0)));
	}
	
	@Test
	public void givenAnGameId_thenfindAllCollectibles_whenGameDoesntExists() throws Exception {
		List<Collectible> collectibles = collRepo.findByGameId(999l);
		
		assertThat(collectibles, not(equalTo(nullValue())));
		assertThat(collectibles, IsCollectionWithSize.hasSize(equalTo(0)));
	}
	
	@Test
	public void givenAnGameId_thenfindAllCollectibles_whenGameDoesntExistsNullValue() throws Exception {
		List<Collectible> collectibles = collRepo.findByGameId(null);
		
		assertThat(collectibles, not(equalTo(nullValue())));
		assertThat(collectibles, IsCollectionWithSize.hasSize(equalTo(0)));
	}
	
	@Test
	public void givenAnGameIdAndCollectedFlag_thenfindAllCollectibles_whenGameHasCollectibles() throws Exception {
		Game game = gameRepo.save(new Game.Builder("Pokemon X", "3DS")
				.addCollectible(Collectible.collected("MewTwo"))
				.addCollectible(Collectible.notCollected("Legendario1"))
				.addCollectible(Collectible.notCollected("Legendario2"))
				.build());
		
		List<Collectible> collected = collRepo.findByGameIdAndIsCollected(game.getId(), true);
		List<Collectible> uncollected = collRepo.findByGameIdAndIsCollected(game.getId(), false);
		
		assertThat(collected, not(equalTo(nullValue())));
		assertThat(collected, IsCollectionWithSize.hasSize(greaterThanOrEqualTo(1)));
		
		assertThat(uncollected, not(equalTo(nullValue())));
		assertThat(uncollected, IsCollectionWithSize.hasSize(greaterThanOrEqualTo(2)));
	}
	
	@Test
	public void givenAnGameIdAndCollectedFlag_thenfindAllCollectibles_whenGameDoesntHaveCollectibles() throws Exception {
		Game game = gameRepo.save(new Game.Builder("Pokemon X", "3DS")
				.build());
		
		List<Collectible> collected = collRepo.findByGameIdAndIsCollected(game.getId(), true);
		List<Collectible> uncollected = collRepo.findByGameIdAndIsCollected(game.getId(), false);
		
		assertThat(collected, not(equalTo(nullValue())));
		assertThat(collected, IsCollectionWithSize.hasSize(equalTo(0)));
		
		assertThat(uncollected, not(equalTo(nullValue())));
		assertThat(uncollected, IsCollectionWithSize.hasSize(equalTo(0)));
	}
	
	@Test
	public void givenAnGameIdAndCollectedFlag_thenfindAllCollectibles_whenGameDoesntExists() throws Exception {
		List<Collectible> collected = collRepo.findByGameIdAndIsCollected(999l, true);
		List<Collectible> uncollected = collRepo.findByGameIdAndIsCollected(999l, false);
		
		assertThat(collected, not(equalTo(nullValue())));
		assertThat(collected, IsCollectionWithSize.hasSize(equalTo(0)));
		
		assertThat(uncollected, not(equalTo(nullValue())));
		assertThat(uncollected, IsCollectionWithSize.hasSize(equalTo(0)));
	}
	
	@Test
	public void givenAnGameIdAndCollectedFlag_thenfindAllCollectibles_whenGameDoesntExistsNullValue() throws Exception {
		List<Collectible> collected = collRepo.findByGameIdAndIsCollected(null, true);
		List<Collectible> uncollected = collRepo.findByGameIdAndIsCollected(null, false);
		
		assertThat(collected, not(equalTo(nullValue())));
		assertThat(collected, IsCollectionWithSize.hasSize(equalTo(0)));
		
		assertThat(uncollected, not(equalTo(nullValue())));
		assertThat(uncollected, IsCollectionWithSize.hasSize(equalTo(0)));
	}
}
