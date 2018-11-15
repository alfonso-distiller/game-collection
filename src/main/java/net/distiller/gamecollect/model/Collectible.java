package net.distiller.gamecollect.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = {"id", "description", "isCollected"})
@Entity
public class Collectible {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String description;
	private boolean isCollected;
	
	private Collectible() {
	}

	private Collectible(String name, String description, boolean isCollected) {
		this.name = name;
		this.description = description;
		this.isCollected = isCollected;
	}
	
	public static Collectible notCollected(String name) {
		return new Collectible(name, null, false);
	}
	
	public static Collectible notCollected(String name, String description) {
		return new Collectible(name, description, false);
	}
	
	public static Collectible collected(String name) {
		return new Collectible(name, null, true);
	}
	
	public static Collectible collected(String name, String description) {
		return new Collectible(name, description, true);
	}
}
