package net.distiller.gamecollect.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.distiller.gamecollect.model.Game;
import net.distiller.gamecollect.model.Platform;
import net.distiller.gamecollect.repositories.GameRepository;

@Service
@AllArgsConstructor
public class GameServiceImpl implements GameService {

	private final GameRepository gameRepository;
	
	@Override
	public List<Game> findAll() {
		return (List<Game>) gameRepository.findAll();
	}

	@Override
	public List<Game> findByPlatform(String platform) {
		return gameRepository.findByPlatform(Platform.of(platform));
	}

	@Override
	public Optional<Game> findById(Long id) {
		return gameRepository.findById(id);
	}

	@Override
	public Game save(Game game) {
		return gameRepository.save(game);
	}

}
