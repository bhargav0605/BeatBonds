package io.beatbonds.model;

import java.time.LocalDateTime;

public class ArtistWithCalculatedPrice {
	
	private String name;
	private Long popularity;
	private Long followers;
	private String image;
	private Double price;
	private LocalDateTime datetime;
	
	public ArtistWithCalculatedPrice() {
		super();
	}
	public ArtistWithCalculatedPrice(String name, Long popularity, Long followers, String image, Double price,
			LocalDateTime datetime) {
		super();
		this.name = name;
		this.popularity = popularity;
		this.followers = followers;
		this.image = image;
		this.price = price;
		this.datetime=datetime;
	}
	
	public LocalDateTime getDatetime() {
		return datetime;
	}
	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
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
		return "ArtistWithCalculatedPrice [name=" + name + ", popularity=" + popularity + ", followers=" + followers
				+ ", image=" + image + ", price=" + price + ", datetime=" + datetime + "]";
	}
}
