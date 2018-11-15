package net.distiller.gamecollect.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.distiller.gamecollect.model.Game;
import net.distiller.gamecollect.model.Platform;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
	List<Game> findByPlatform(Platform platform);
}
