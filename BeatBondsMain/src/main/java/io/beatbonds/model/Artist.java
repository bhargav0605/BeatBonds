package io.beatbonds.model;

public class Artist {
	
	private Long artistId;
	private String name;
	private Long popularity;
	private Long followers;
	private String image;
	
	public Artist() {
		super();
	}
	
	public Artist(Long artistId, String name, Long popularity, Long followers, String image) {
		super();
		this.artistId = artistId;
		this.name = name;
		this.popularity = popularity;
		this.followers = followers;
		this.image = image;
	}
	
	public Long getArtistId() {
		return artistId;
	}
	
	public void setArtistId(Long artistId) {
		this.artistId = artistId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Long getPopularity() {
		return popularity;
	}
	
	public void setPopularity(Long popularity) {
		this.popularity = popularity;
	}
	
	public Long getFollowers() {
		return followers;
	}
	
	public void setFollowers(Long followers) {
		this.followers = followers;
	}
	
	public String getImage() {
		return image;
	}
	
	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
	public String toString() {
		return "Artist [artistId=" + artistId + ", name=" + name + ", popularity=" + popularity + ", followers="
				+ followers + ", image=" + image + "]";
	}
}
