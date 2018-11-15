package net.distiller.gamecollect.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import net.distiller.gamecollect.model.Collectible;
import net.distiller.gamecollect.services.CollectibleService;

@RestController
@RequestMapping(value = "/games/{id}/collectibles")
@AllArgsConstructor
public class CollectibleController {
	private final CollectibleService colService;
	
	@RequestMapping(method = RequestMethod.GET,
			produces = {"application/json"})
	public List<Collectible> findByGameId(@PathVariable(name = "id") Long id) {
		return colService.findByGameId(id);
	}
	
	@RequestMapping(method = RequestMethod.GET,
			path = "/{flag}",
			produces = {"application/json"})
	public List<Collectible> findByGameIdWithFlag(@PathVariable(name = "id") Long id, @PathVariable(name = "flag") boolean flag) {
		return colService.findByGameIdAndIsCollected(id, flag);
	}
}
