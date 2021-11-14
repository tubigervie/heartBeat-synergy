package com.revature.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.revature.models.HBMatch;
import com.revature.models.HBTopArtist;
import com.revature.models.HBTopGenre;
import com.revature.models.HBUserAccount;
import com.revature.models.HBUserImage;
import com.revature.services.HBUserService;

@CrossOrigin(origins="*", allowedHeaders="*")
@RestController
@RequestMapping(value="/account")
public class HBUserController 
{
	private HBUserService userService;
	
	@Autowired
	public HBUserController(HBUserService service)
	{
		this.userService = service;
	}
	
	@GetMapping("/{id}/genres")
	public List<HBTopGenre> getAccountTopGenres(@PathVariable("id") int id){
		List<HBTopGenre> genres = userService.findTopGenresByUserId(id);
		return genres;
	}
	
	@GetMapping("/{id}/potentials")
	public Set<HBUserAccount> getPotentialMatches(@PathVariable("id") int id)
	{
		HBUserAccount account = userService.findAccountById(id);
		return userService.findAllOtherUserAccountsOfSameGenres(account);
	}
	
	@PostMapping("/{id}/genre")
	public ResponseEntity<HBUserAccount> addTopGenreToAccount(@PathVariable("id") int id, @RequestBody HBTopGenre genre)
	{
		HBUserAccount account = userService.findAccountById(id);
		if(account == null) return ResponseEntity.status(400).build();
		genre.setUser(account);
		boolean isAdded = userService.addGenre(genre);
		if(!isAdded) {
			return ResponseEntity.status(400).build();
		}
		else {
			return ResponseEntity.status(201).build();
		}
	}
	
	@PostMapping("/{id}/genres")
	public ResponseEntity<HBUserAccount> addTopGenresToAccount(@PathVariable("id") int id, @RequestBody List<HBTopGenre> genres)
	{
		HBUserAccount account = userService.findAccountById(id);
		if(account == null) {
			return ResponseEntity.status(400).build();
		}
		userService.deleteHBUserTopGenres(account);
		for(HBTopGenre genre : genres)
		{
			genre.setUser(account);
		}
		System.out.println("added genres");
		return ResponseEntity.status(200).build();
	}
	
	@DeleteMapping("/{id}/genres")
	public ResponseEntity<HBUserAccount> deleteTopGenresFromAccount(@PathVariable("id") int id)
	{
		HBUserAccount account = userService.findAccountById(id);
		if(account == null) {
			return ResponseEntity.status(400).build();
		}
		userService.deleteHBUserTopGenres(account);
		return ResponseEntity.status(200).build();
	}

	
	@GetMapping
	public List<HBUserAccount> getAllAccounts()
	{
		return userService.findAllUserAccounts();
	}
	
	@GetMapping("/{id}")
	public HBUserAccount getAccount(@PathVariable("id") int id)
	{
		HBUserAccount account = userService.findAccountById(id);
		return account;
	}
	
	@GetMapping("/{id}/artists")
	public List<HBTopArtist> getAccountTopArtists(@PathVariable("id") int id)
	{
		List<HBTopArtist> artists = userService.findTopArtistsByUserID(id);
		return artists;
	}
	
	@PostMapping("/{id}/artist")
	public ResponseEntity<HBUserAccount> addTopArtistToAccount(@PathVariable("id") int id, @RequestBody HBTopArtist artist)
	{
		HBUserAccount account = userService.findAccountById(id);
		if(account == null) return ResponseEntity.status(400).build();
		boolean clearedPreviousArtists = userService.deleteHBUserTopArtists(account);
		if(!clearedPreviousArtists) return ResponseEntity.status(400).build();
		artist.setUser(account);
		boolean isAdded = userService.addOrUpdateHBUserTopArtist(artist);
		if(!isAdded)
			return ResponseEntity.status(400).build();
		return ResponseEntity.status(201).build();
	}
	
	@PostMapping("/{id}/artists")
	public ResponseEntity<HBUserAccount> addTopArtistsToAccount(@PathVariable("id") int id, @RequestBody List<HBTopArtist> artists)
	{
		HBUserAccount account = userService.findAccountById(id);
		if(account == null) return ResponseEntity.status(400).build();
		boolean clearedPreviousArtists = userService.deleteHBUserTopArtists(account);
		if(!clearedPreviousArtists) return ResponseEntity.status(400).build();
		for(HBTopArtist artist : artists)
		{
			artist.setUser(account);
		}
		boolean addedAllArtists = userService.addHBUserTopArtists(artists);
		if(!addedAllArtists) return ResponseEntity.status(400).build();
		return ResponseEntity.status(200).build();
	}
	
	@DeleteMapping("/{id}/artists")
	public ResponseEntity<HBUserAccount> deleteTopArtistsFromAccount(@PathVariable("id") int id)
	{
		HBUserAccount account = userService.findAccountById(id);
		if(account == null) return ResponseEntity.status(400).build();
		boolean clearedPreviousArtists = userService.deleteHBUserTopArtists(account);
		if(!clearedPreviousArtists) return ResponseEntity.status(400).build();
		return ResponseEntity.status(200).build();
	}
	
	@PostMapping
	public ResponseEntity<HBUserAccount> addAccount(@RequestBody HBUserAccount account)
	{
		HBUserAccount addedAccount = userService.addOrUpdateHBUserAccount(account);
		if(addedAccount == null)
			return ResponseEntity.status(400).build();
		return new ResponseEntity<HBUserAccount>(addedAccount, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}/photo" , method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public ResponseEntity<HBUserAccount> addPhotoData(@PathVariable("id") int id, @RequestPart("image") MultipartFile multipartFile)
	{
		HBUserAccount account = userService.findAccountById(id);
		if(account == null) return ResponseEntity.status(400).build();
		userService.deleteUserImages(account);
		try {
			userService.storeImage(account, multipartFile);
		} catch (IOException e) {
			return ResponseEntity.status(400).build();
		}
		return ResponseEntity.status(201).build();
	}
	
	@DeleteMapping(value="/{id}/photo")
	public ResponseEntity<HBUserAccount> deleteImages(@PathVariable("id") int id)
	{
		HBUserAccount account = userService.findAccountById(id);
		if(account == null) return ResponseEntity.status(400).build();
		userService.deleteUserImages(account);
		return ResponseEntity.status(201).build();
	}
	
	@GetMapping(value = "/{id}/photo" )
	public List<HBUserImage> getAccountPhotos(@PathVariable("id") int id)
	{
		HBUserAccount account = userService.findAccountById(id);
		if(account == null) return null;
		return userService.getImagesByUser(account);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HBUserAccount> deleteAccount(@PathVariable("id") int id)
	{
		boolean isDeleted = userService.deleteHBUserAccount(id);
		if(!isDeleted)
			return ResponseEntity.status(400).build();
		return ResponseEntity.status(201).build();
	}
	
	@PostMapping("/{id}/match")
	public ResponseEntity<HBMatch> addOrUpdateMatch(@PathVariable("id") int id, @RequestBody HBMatch match)
	{
		HBUserAccount account = userService.findAccountById(id);
		if(account == null) return ResponseEntity.status(400).build();
		boolean isAdded = userService.addOrUpdateMatch(match);
		if(!isAdded)
			return ResponseEntity.status(400).build();
		return ResponseEntity.status(201).build();
	}
	
	@GetMapping("/{id}/match/success")
	public List<HBUserAccount> getSuccessfulMatches(@PathVariable("id") int id)
	{
		HBUserAccount account = userService.findAccountById(id);
		return userService.findAllOtherMatchedAccounts(account);
	}
	
	@GetMapping("/{id}/match")
	public List<HBUserAccount> getPendingMatches(@PathVariable("id") int id)
	{
		HBUserAccount account = userService.findAccountById(id);
		return userService.findAllOtherPendingAccounts(account);
	}
	
	@GetMapping("/{id}/match/{oid}")
	public ResponseEntity<HBMatch> getMatchByCombination(@PathVariable("id") int id, @PathVariable("oid") int oid)
	{
		HBUserAccount userAccount = userService.findAccountById(id);
		HBUserAccount otherAccount = userService.findAccountById(oid);
		HBMatch match = userService.findExistingMatchByCombination(userAccount, otherAccount);
		if(match == null) return ResponseEntity.status(400).build();
		return new ResponseEntity<HBMatch>(match, HttpStatus.OK);
	}
	
}
