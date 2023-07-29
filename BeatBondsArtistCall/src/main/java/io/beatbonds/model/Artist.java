package io.beatbonds.model;

public class Artist {
	
//	private Integer id;
	private String name;

	public Artist() {
		super();
	}
//	 Integer id
	public Artist(String name) {
		super();
		this.name = name;
//		this.id=id;
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

//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}

//	@Override
//	public String toString() {
//		return "Artist [id=" + id + ", name=" + name + "]";
//	}
	
	
}
