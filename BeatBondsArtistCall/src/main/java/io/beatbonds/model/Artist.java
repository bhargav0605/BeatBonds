package io.beatbonds.model;

public class Artist {
	
	private String name;

	public Artist() {
		super();
	}

	public Artist(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Artist [name=" + name + "]";
	}
	
	
}
