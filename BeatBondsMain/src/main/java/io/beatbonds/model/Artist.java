package io.beatbonds.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name="artists_details_pricing")
@Entity
public class Artist {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="artist_id")
	private Long artistId;
	
	@Column(name = "artist")
	private String name;
	
	@Column(name = "popularity")
	private Long popularity;
	
	@Column(name = "followers")
	private Long followers;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "price")
	private double price;
	
//	@JsonIgnore
	@Column(name="datetime")
	private LocalDateTime datetime;
	
	public Artist() {
		super();
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	@Override
	public String toString() {
		return "Artist [artistId=" + artistId + ", name=" + name + ", popularity=" + popularity + ", followers="
				+ followers + ", image=" + image + ", price=" + price + ", datetime=" + datetime + "]";
	}
}
