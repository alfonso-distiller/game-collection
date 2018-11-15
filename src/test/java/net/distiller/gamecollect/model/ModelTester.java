package net.distiller.gamecollect.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.Test;

public class ModelTester {
	
	@Test
	public void buildGamePlatform() throws Exception {
		Platform game = Platform.of("3DS");
				
		assertThat(game, not(equalTo(nullValue())));
		assertThat(game, hasProperty("platform", equalTo("3DS")));
	}
	
	@Test
	public void buildGameItemAllStringsConstructor() throws Exception {
		Game game = Game.of("Fire Emblem: Awakening", "3DS");
		
		assertThat(game, not(equalTo(nullValue())));
		assertThat(game, hasProperty("title", equalTo("Fire Emblem: Awakening")));
		assertThat(game, hasProperty("platform", hasProperty("platform", equalTo("3DS"))));
	}
	
	@Test
	public void buildGameItemString() throws Exception {
		Game game = Game.of("Fire Emblem: Awakening", Platform.of("3DS"));
		
		assertThat(game, not(equalTo(nullValue())));
		assertThat(game, hasProperty("title", equalTo("Fire Emblem: Awakening")));
		assertThat(game, hasProperty("platform", hasProperty("platform", equalTo("3DS"))));
	}
	
	@Test
	public void gameBuilder() throws Exception {
		Game game = new Game.Builder("Pokemon X", "3DS")
				.addCollectible(Collectible.collected("MewTwo"))
				.addCollectible(Collectible.notCollected("Legendario1"))
				.addCollectible(Collectible.notCollected("Legendario2"))
				.build();
		
		assertThat(game, not(equalTo(nullValue())));
		assertThat(game, hasProperty("title", equalTo("Pokemon X")));
		assertThat(game, hasProperty("platform", hasProperty("platform", equalTo("3DS"))));
		
		assertThat(game, hasProperty("collectibles", IsCollectionWithSize.hasSize(3)));
	}
}
