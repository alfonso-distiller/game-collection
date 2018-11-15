package net.distiller.gamecollect.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import net.distiller.gamecollect.model.Game;
import net.distiller.gamecollect.services.GameService;

@RestController
@RequestMapping(value = "/games")
@AllArgsConstructor
public class GameController {
	
	private final GameService gameService;
	
	@RequestMapping(method = RequestMethod.GET,
			produces = {"application/json"})
	public List<Game> findAllGames() throws Exception {
		return gameService.findAll();
	}
	
	@RequestMapping(method = RequestMethod.GET,
			path = "/platform/{platform}",
			produces = {"application/json"})
	public List<Game> findByPlatform(@PathVariable(name = "platform") String platform) {
		return gameService.findByPlatform(platform);
	}
	
	@RequestMapping(method = RequestMethod.GET,
			path = "/{id}",
			produces = {"application/json"})
	public ResponseEntity<Game> findById(@PathVariable(name = "id") Long id) {
		Optional<Game> game = gameService.findById(id);
		return game.isPresent() ? ResponseEntity.ok(game.get()) : ResponseEntity.notFound().build();
	}
	
	@RequestMapping(method = RequestMethod.POST, 
			produces = {"application/json"})
	public ResponseEntity<Game> save(@RequestBody Game game) {
		return ResponseEntity.ok(gameService.save(game));
	}
	
	@RequestMapping(method = RequestMethod.POST, 
			path = "/{id}",
			produces = {"application/json"})
	public ResponseEntity<Game> update(@RequestBody Game game, @PathVariable Long id) {
		Optional<Game> gameFound = gameService.findById(id);
		
		if(gameFound.isPresent()) {
			return ResponseEntity.ok(gameService.save(game));
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
