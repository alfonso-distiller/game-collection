package net.distiller.gamecollect.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString(exclude = {"collectibles"})
public class Game {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String title;
	
	@Embedded
	private Platform platform;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "game_id")
	private List<Collectible> collectibles = new ArrayList<>();

	private Game() {
	}

	private Game(String title, String platform) {
		this.title = title;
		this.platform = Platform.of(platform);
	}

	private Game(String title, Platform platform) {
		this.title = title;
		this.platform = platform;
	}
	
	private Game(Builder builder) {
		this.title = builder.title;
		this.platform = Platform.of(builder.platform);
		this.collectibles = builder.collectibles;
	}
	
	public static Game of(String title, String platform) {
		return new Game(title, platform);
	}

	public static Game of(String title, Platform platform) {
		return new Game(title, platform);
	}
	
	public void addCollectible(Collectible ... collectible) {
		Arrays.asList(collectible).stream().forEach(this.collectibles::add);
	}
	
	public void removeCollectible(Collectible ...collectible) {
		Arrays.asList(collectible).stream().forEach(this.collectibles::remove);
	}
	
	public void removeAllCollectibles() {
		this.collectibles.clear();
	}
	
	public static class Builder {
		private final String title;
		private final String platform;
		private final List<Collectible> collectibles;
		
		public Builder(String title, String platform) {
			super();
			this.title = title;
			this.platform = platform;
			this.collectibles = new ArrayList<>(); 
		}
		
		public Builder addCollectible(Collectible collectible) {
			collectibles.add(collectible);
			return this;
		}
		
		public Builder addCollectibles(Collection<Collectible> collectibles) {
			collectibles.stream().forEach(this.collectibles::add);
			return this;
		}
		
		public Game build() {
			return new Game(this);
		}
	}
}
