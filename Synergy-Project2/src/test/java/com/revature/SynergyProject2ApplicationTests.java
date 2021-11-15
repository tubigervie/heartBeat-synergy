package com.revature;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.revature.controllers.HBLoginController;
import com.revature.models.HBLoginDTO;
import com.revature.models.HBMatch;
import com.revature.models.HBTopArtist;
import com.revature.models.HBTopGenre;
import com.revature.models.HBUserAccount;
import com.revature.repos.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.revature.services.HBUserService;


@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
class SynergyProject2ApplicationTests {

	
	
	@Autowired
	private HBUserService userService;
	@Autowired
	private HBLoginController loginController;
	

	//User tests
	
	private static Logger log = LoggerFactory.getLogger(SynergyProject2ApplicationTests.class);
	HBUserAccount userAccount = new HBUserAccount("hbUserTester", "testpass", "testname", "testlast", 0, "testDesc", "testList", "testAnthem", null, null, "EVERYONE", "EVERYONE");
	HBTopArtist topArtist = new HBTopArtist(0, "testArtistId", "testArtistName", "test", null);
	HBTopGenre topGenre = new HBTopGenre("test");
	HBMatch match = new HBMatch(userAccount.getId(), userAccount.getId(), "ACCEPT", "ACCEPT");
	HBLoginDTO logindto = new HBLoginDTO("hbUserTester", "testpass");


	
	@BeforeAll
	public void AddAccount(){
		HBUserAccount test = userService.addOrUpdateHBUserAccount(userAccount);
		assertEquals(test,userAccount);
	}
	
	@Test
	public void getAllAccounts() {
		List<HBUserAccount> list = userService.findAllUserAccounts();
		assertNotEquals(list, 0);
	}
	
	@Test
	public void getAccountById() {
		HBUserAccount test = userService.findAccountById(userAccount.getId());
		assertEquals(test, userAccount);
	}
	
	@Test 
	public void getAccountByUsername() {
		HBUserAccount test = userService.findAccountByUsername(userAccount.getUsername());
		
		assertEquals(test, userAccount);
	}
	
	//Artist tests
	@Test
	public void addTopArtistTest(){
		List<HBTopArtist> list = new ArrayList<HBTopArtist>();
		list.add(topArtist);
		assertTrue(userService.addHBUserTopArtists(list));
	}
	
	@Test
	public void addHBTopArtistTest() {
		List<HBTopArtist> list = new ArrayList<HBTopArtist>();
		list.add(topArtist);
		assertTrue(userService.addHBUserTopArtists(list));
	}
	
	@Test
	public void deleteHBTopArtistTest() {
		assertTrue(userService.deleteHBUserTopArtists(userAccount));	
	}
	
	@Test
	public void findTopArtistByUserIdTest(){
		List<HBTopArtist> list = new ArrayList<HBTopArtist>();
		list = userService.findTopArtistsByUserID(userAccount.getId());
		assertEquals(list.size(), 0);
	}
	
	//Genre tests
	@Test
	public void addGenreTest() {
		assertTrue(userService.addGenre(topGenre));
	}
	
	@Test
	public void addHBTopGenres() {
		List<HBTopGenre> list = new ArrayList<HBTopGenre>();
		list.add(topGenre);
		assertTrue(userService.addHBUserTopGenres(list));
	}
	
	@Test
	public void findTopGenresByUserIdTest() {
		List<HBTopGenre> list = new ArrayList<HBTopGenre>();
		list = userService.findTopGenresByUserId(userAccount.getId());
		assertEquals(list.size(), 0);
	}
	
	
	//Match tests
	@Test
	public void addMatch() {
	 assertTrue(userService.addOrUpdateMatch(match));
	}
	
	@Test
	public void findMatchByUsers() {
		userService.findExistingMatchByCombination(userAccount.getId(), userAccount.getId());
		assertFalse(userService.findExistingMatchByCombination(userAccount.getId(), userAccount.getId())==null);
	}
	
	
	@Test
	public void findAllOtherMatchedAccountsTest() {
		assertEquals(userService.findAllOtherMatchedAccounts(userAccount).size(),1);
	}
	

	@Test
	public void findAllOtherPendingAccountsTest() {
		assertEquals(userService.findAllOtherMatchedAccounts(userAccount).size(), 1);
	}
	
	//Login tests
	@Test
	public void loginTest() {
		assertEquals(loginController.loginToAccount(logindto),userAccount);
	}
	


	@AfterAll
	public void DeleteAccount() {
		userService.deleteHBUserAccount(userAccount.getId());
	}

}
