package net.distiller.gamecollect.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import net.distiller.gamecollect.model.Collectible;
import net.distiller.gamecollect.repositories.CollectibleRepository;

@AllArgsConstructor
@Service
public class CollectibleServiceImpl implements CollectibleService {

	private final CollectibleRepository repo;

	@Override
	public List<Collectible> findByGameId(Long id) {
		return repo.findByGameId(id);
	}

	@Override
	public List<Collectible> findByGameIdAndIsCollected(Long id, boolean isCollected) {
		return repo.findByGameIdAndIsCollected(id, isCollected);
	}
}
