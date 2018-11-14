package net.distiller.gamecollect.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.distiller.gamecollect.model.Collectible;

@Repository
public interface CollectibleRepository extends CrudRepository<Collectible, Long> {
	
	@Query("select g.collectibles from Game g where g.id = ?1")
	List<Collectible> findByGameId(Long id);
	
	@Query("select distinct c from Game g join g.collectibles c where g.id = :id and c.isCollected = :isCollected")
	List<Collectible> findByGameIdAndIsCollected(Long id, boolean isCollected);
}
