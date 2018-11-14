package net.distiller.gamecollect.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = {"platform"})
@Embeddable
public class Platform {

	@NotNull
	private String platform;

	private Platform() {
	}

	private Platform(String platform) {
		this.platform = platform;
	}

	public static Platform of(String platform) {
		return new Platform(platform);
	}
}
