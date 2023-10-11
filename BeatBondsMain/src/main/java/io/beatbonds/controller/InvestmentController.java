package io.beatbonds.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.beatbonds.model.Artist;
import io.beatbonds.model.Investment;
import io.beatbonds.model.User;
import io.beatbonds.repository.ArtistRepository;
import io.beatbonds.repository.InvestmentRepository;
import io.beatbonds.repository.UserRepository;

@RestController
@RequestMapping("/api/v1")
public class InvestmentController {
	
	private InvestmentRepository investmentRepository;
	
	private UserRepository userReposiitory;
	
	private ArtistRepository artistRepository;
	
	public InvestmentController(InvestmentRepository investmentRepository, UserRepository userReposiitory, ArtistRepository artistRepository) {
		this.investmentRepository=investmentRepository;
		this.userReposiitory=userReposiitory;
		this.artistRepository=artistRepository;
	}
	
	@PostMapping("/invest/{userId}/{artistId}")
	public ResponseEntity<String> invest(@PathVariable(value = "userId") Long userId,
			@PathVariable(value = "artistId") Long artistId,
			@RequestBody Investment investment){
		User usr = userReposiitory.findById(userId).orElseThrow(null);
		Artist art = artistRepository.findById(artistId).orElseThrow(null);
//		Investment invest = userReposiitory.findById(userId).map(tt -> {
//			tt.getAllInvestedArtists().add(investment)
//		})
		
		Investment inv = new Investment(usr, art, investment.getAmountInvested(), LocalDateTime.now());
//		inv.se
		investmentRepository.save(inv);
		
		return new ResponseEntity<>("Invested", HttpStatus.ACCEPTED);
	}

}
