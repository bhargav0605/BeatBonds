package io.beatbonds.model;

public class ArtistFromSpotify {
	
	private String name;
	private Long popularity;
	private Long followers;
	private String image;
	
	public ArtistFromSpotify() {
		super();
	}
	public ArtistFromSpotify(String name, Long popularity, Long followers, String image) {
		super();
		this.name = name;
		this.popularity = popularity;
		this.followers = followers;
		this.image = image;
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
		return "ArtistDb [name=" + name + ", popularity=" + popularity + ", followers=" + followers + ", image=" + image
				+ "]";
	}
}
