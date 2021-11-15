package com.revature.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.HBLoginDTO;
import com.revature.models.HBUserAccount;
import com.revature.services.HBUserService;

@CrossOrigin(origins="*", allowedHeaders="*")
@RestController
@RequestMapping(value="/login")
public class HBLoginController 
{
	
	private HBUserService userService;
	
	@Autowired
	public HBLoginController(HBUserService service)
	{
		this.userService = service;
	}
	
	@PostMapping
	public ResponseEntity<HBUserAccount> loginToAccount(@RequestBody HBLoginDTO login)
	{
		//insert password encryption logic here
		HBUserAccount userAccount = userService.findAccountByUsername(login.username);
		if(userAccount == null || !userAccount.getPassword().equals(login.password))
			return ResponseEntity.status(400).build();
		return new ResponseEntity<HBUserAccount>(userAccount, HttpStatus.ACCEPTED);
	}
}
