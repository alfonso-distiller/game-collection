package net.distiller.gamecollect.bootstrap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.distiller.gamecollect.model.Collectible;
import net.distiller.gamecollect.model.Game;
import net.distiller.gamecollect.model.Platform;
import net.distiller.gamecollect.repositories.GameRepository;

@Profile("InMemory")
@Component
@Slf4j
@PropertySource("classpath:application.properties")
public class InMemoryDataSetup implements CommandLineRunner {

	@Value("${inmemory.data.setup.message}")
	private String settingUpData;
	private final GameRepository gameItemRepository;
	
	public InMemoryDataSetup(GameRepository gameItemRepository) {
		super();
		this.gameItemRepository = gameItemRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		log.info(settingUpData);
		gameItemRepository.save(Game.of("Fire Emblem: Awakening", Platform.of("3DS")));
		gameItemRepository.save(Game.of("The Legend of Zelda", Platform.of("Nintendo Switch")));
		gameItemRepository.save(Game.of("Diablo III", Platform.of("Nintendo Switch")));
		gameItemRepository.save(Game.of("Diablo III", Platform.of("PC")));
		gameItemRepository.save(new Game.Builder("Pokemon X", "3DS")
				.addCollectible(Collectible.collected("MewTwo"))
				.addCollectible(Collectible.notCollected("Legendario1"))
				.addCollectible(Collectible.notCollected("Legendario2"))
				.build());
	}	
}
