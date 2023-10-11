package io.beatbonds.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="investments")
public class Investment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "investment_id")
	private Long investmentId;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "artist_id")
	private Artist artist;
	
	@Column(name = "amount_invested")
	private Long amountInvested;
	
	@Column(name = "investment_date")
	private LocalDateTime investmentDate;

	public Investment() {
		super();
	}

	public Investment(Long amountInvested, LocalDateTime investmentDate) {
		super();
		this.amountInvested = amountInvested;
		this.investmentDate = investmentDate;
	}
	
	
	public Investment(User user, Artist artist, Long amountInvested, LocalDateTime investmentDate) {
		super();
		this.user = user;
		this.artist = artist;
		this.amountInvested = amountInvested;
		this.investmentDate = investmentDate;
	}

	public Long getInvestmentId() {
		return investmentId;
	}

	public void setInvestmentId(Long investmentId) {
		this.investmentId = investmentId;
	}

	public Long getAmountInvested() {
		return amountInvested;
	}

	public void setAmountInvested(Long amountInvested) {
		this.amountInvested = amountInvested;
	}

	public LocalDateTime getInvestmentDate() {
		return investmentDate;
	}

	public void setInvestmentDate(LocalDateTime investmentDate) {
		this.investmentDate = investmentDate;
	}

	public Long getUser() {
		return this.user.getUserId();
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getArtist() {
		return this.artist.getArtistId();
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}
}
