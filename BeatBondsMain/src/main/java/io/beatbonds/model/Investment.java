package io.beatbonds.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//@Entity
//@Table(name="investments")
public class Investment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long investmentId;
	
	private Long userId;
	
	private Long artistId;
	
	private Long amountInvested;
	
	private Date investmentDate;

	public Investment() {
		super();
	}

	public Investment(Long investmentId, Long userId, Long artistId, Long amountInvested, Date investmentDate) {
		super();
		this.investmentId = investmentId;
		this.userId = userId;
		this.artistId = artistId;
		this.amountInvested = amountInvested;
		this.investmentDate = investmentDate;
	}

	public Long getInvestmentId() {
		return investmentId;
	}

	public void setInvestmentId(Long investmentId) {
		this.investmentId = investmentId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getArtistId() {
		return artistId;
	}

	public void setArtistId(Long artistId) {
		this.artistId = artistId;
	}

	public Long getAmountInvested() {
		return amountInvested;
	}

	public void setAmountInvested(Long amountInvested) {
		this.amountInvested = amountInvested;
	}

	public Date getInvestmentDate() {
		return investmentDate;
	}

	public void setInvestmentDate(Date investmentDate) {
		this.investmentDate = investmentDate;
	}

	@Override
	public String toString() {
		return "Investment [investmentId=" + investmentId + ", userId=" + userId + ", artistId=" + artistId
				+ ", amountInvested=" + amountInvested + ", investmentDate=" + investmentDate + "]";
	}
}
