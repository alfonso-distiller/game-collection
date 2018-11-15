package net.distiller.gamecollect.services;

import java.util.List;

import net.distiller.gamecollect.model.Collectible;

public interface CollectibleService {
	List<Collectible> findByGameId(Long id);
	List<Collectible> findByGameIdAndIsCollected(Long id, boolean isCollected);
}
