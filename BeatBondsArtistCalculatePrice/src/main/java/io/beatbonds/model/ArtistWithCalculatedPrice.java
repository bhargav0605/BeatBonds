package io.beatbonds.model;

public class ArtistWithCalculatedPrice {
	
	private String name;
	private Long popularity;
	private Long followers;
	private String image;
//	private Long price;
	private Double price;
	public ArtistWithCalculatedPrice() {
		super();
	}
	public ArtistWithCalculatedPrice(String name, Long popularity, Long followers, String image, Double price) {
		super();
		this.name = name;
		this.popularity = popularity;
		this.followers = followers;
		this.image = image;
		this.price = price;
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "ArtistPricedDb [name=" + name + ", popularity=" + popularity + ", followers=" + followers + ", image="
				+ image + ", price=" + price + "]";
	}
}
