package net.distiller.gamecollect.services;

import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.hamcrest.collection.IsCollectionWithSize;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.distiller.gamecollect.model.Collectible;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CollectibleServiceIT {
	
	@Autowired
	CollectibleService colService;
	
	@Test
	public void findEmptyCollectibles() throws Exception {
		List<Collectible> collectibles = colService.findByGameId(1l);
		
		assertThat(collectibles, IsEmptyCollection.emptyCollectionOf(Collectible.class));
	}
	
	@Test
	public void findEmptyCollectiblesNull() throws Exception {
		List<Collectible> collectibles = colService.findByGameId(null);
		
		assertThat(collectibles, IsEmptyCollection.emptyCollectionOf(Collectible.class));
	}
	
	@Test
	public void findAllCollectibles() throws Exception {
		List<Collectible> collectibles = colService.findByGameId(5l);
		
		assertThat(collectibles, IsCollectionWithSize.hasSize(3));
	}
	
	@Test
	public void findEmptyCollectiblesCollected() throws Exception {
		List<Collectible> collectibles = colService.findByGameIdAndIsCollected(1l, true);
		
		assertThat(collectibles, IsEmptyCollection.emptyCollectionOf(Collectible.class));
	}
	
	@Test
	public void findEmptyCollectiblesNullCollected() throws Exception {
		List<Collectible> collectibles = colService.findByGameIdAndIsCollected(null, true);
		
		assertThat(collectibles, IsEmptyCollection.emptyCollectionOf(Collectible.class));
	}
	
	@Test
	public void findAllCollectiblesCollected() throws Exception {
		List<Collectible> collectibles = colService.findByGameIdAndIsCollected(5l, true);
		
		collectibles.stream().forEach(System.out::println);
		
		assertThat(collectibles, IsCollectionWithSize.hasSize(1));
	}
}
