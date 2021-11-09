package com.revature.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.HBTopArtist;
import com.revature.models.HBUserAccount;
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
	
	@PostMapping("/{id}/artists")
	public ResponseEntity<HBUserAccount> addTopArtistToAccount(@RequestBody HBTopArtist artist)
	{
		boolean isAdded = userService.addOrUpdateHBUserTopArtist(artist);
		if(!isAdded)
			return ResponseEntity.status(400).build();
		return ResponseEntity.status(201).build();
	}
	
	@PostMapping
	public ResponseEntity<HBUserAccount> addAccount(@RequestBody HBUserAccount account)
	{
		boolean isAdded = userService.addOrUpdateHBUserAccount(account);
		if(!isAdded)
			return ResponseEntity.status(400).build();
		return ResponseEntity.status(201).build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HBUserAccount> deleteAccount(@PathVariable("id") int id)
	{
		boolean isDeleted = userService.deleteHBUserAccount(id);
		if(!isDeleted)
			return ResponseEntity.status(400).build();
		return ResponseEntity.status(201).build();
	}
	
}
