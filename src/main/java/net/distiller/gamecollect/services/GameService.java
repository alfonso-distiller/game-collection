package net.distiller.gamecollect.services;

import java.util.List;
import java.util.Optional;

import net.distiller.gamecollect.model.Game;

public interface GameService {
	List<Game> findAll();
	List<Game> findByPlatform(String platform);
	Optional<Game> findById(Long id);
	Game save(Game game);
}
