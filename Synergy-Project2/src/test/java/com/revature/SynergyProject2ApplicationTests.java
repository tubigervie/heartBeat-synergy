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

	//17 out of 22 methods have tests
	
	@Autowired
	private HBUserService userService;
	@Autowired
	private HBLoginController loginController;
	

	//User tests
	
	private static Logger log = LoggerFactory.getLogger(SynergyProject2ApplicationTests.class);
	HBUserAccount userAccount = new HBUserAccount("hbUserTester1", "testpass", "testname", "testlast", 0, "testDesc", "testList", "testAnthem", null, null, "EVERYONE", "EVERYONE");
	HBTopArtist topArtist = new HBTopArtist(0, "testArtistId", "testArtistName", "test", null);
	HBTopGenre topGenre = new HBTopGenre("test");
	HBMatch match = new HBMatch(userAccount, userAccount, true, true);
	HBLoginDTO logindto = new HBLoginDTO("hbUserTester", "testpass");


	
	@Test
	public void AddAccount(){
		HBUserAccount test = userService.addOrUpdateHBUserAccount(userAccount);
		assertEquals(test,userAccount);
		log.info("addAccount testing");
		log.info("User Id: " + String.valueOf(userAccount.getId()));

	}
	
	@Test
	public void getAllAccounts() {
		List<HBUserAccount> list = userService.findAllUserAccounts();
		assertNotEquals(list, 0);
		log.info("getAllAccounts testing");
		log.info("List size: " + String.valueOf(list.size()));
	}
	
	@Test
	public void getAccountById() {
		HBUserAccount test = userService.findAccountById(userAccount.getId());
		assertEquals(test, userAccount);
		log.info("getAccountById testing");
	}
	
	@Test 
	public void getAccountByUsername() {
		HBUserAccount test = userService.findAccountByUsername(userAccount.getUsername());
		assertEquals(test, userAccount);
		log.info("getAccountByUsername testing");
	}
	
	//Artist tests
	@Test
	public void addTopArtistTest(){
		List<HBTopArtist> list = new ArrayList<HBTopArtist>();
		list.add(topArtist);
		assertTrue(userService.addHBUserTopArtists(list));
		log.info("addTopArtist testing");
	}
	
	@Test
	public void addHBTopArtistTest() {
		List<HBTopArtist> list = new ArrayList<HBTopArtist>();
		list.add(topArtist);
		assertTrue(userService.addHBUserTopArtists(list));
		log.info("userService testing");
		log.info("List: " +list.toString());
	}
	
	@Test
	public void deleteHBTopArtistTest() {
		assertTrue(userService.deleteHBUserTopArtists(userAccount));	
		log.info("userService testing");
	}
	
	@Test
	public void findTopArtistByUserIdTest(){
		List<HBTopArtist> list = new ArrayList<HBTopArtist>();
		list = userService.findTopArtistsByUserID(userAccount.getId());
		assertEquals(list.size(), 0);
		log.info("findTopArtist testing");
	}
	
	//Genre tests
	@Test
	public void addGenreTest() {
		assertTrue(userService.addGenre(topGenre));
		log.info("addGenre testing");
	}
	
	@Test
	public void addHBTopGenres() {
		List<HBTopGenre> list = new ArrayList<HBTopGenre>();
		list.add(topGenre);
		assertTrue(userService.addHBUserTopGenres(list));
		log.info("addHBTopGenre testing");
		log.info("Genre list: " +list.toString());
	}
	
	@Test
	public void findTopGenresByUserIdTest() {
		List<HBTopGenre> list = new ArrayList<HBTopGenre>();
		list = userService.findTopGenresByUserId(userAccount.getId());
		assertEquals(list.size(), 0);
		log.info("findTopGenresByUser testing");
	}
	
	
	//Match tests
	@Test
	public void addMatch() {
	 assertTrue(userService.addOrUpdateMatch(match));
	 log.info("addMatch testing");
	 log.info("Match Id: " + String.valueOf(match.getId()));
	}
	
	@Test
	public void findMatchByUsers() {
		userService.findExistingMatchByCombination(userAccount, userAccount);
		assertFalse(userService.findExistingMatchByCombination(userAccount, userAccount)==null);
		log.info("findMatchByUser testing");
	}
	
	
	@Test
	public void findAllOtherMatchedAccountsTest() {
		assertEquals(userService.findAllOtherMatchedAccounts(userAccount).size(),1);
		log.info("findAllOtherMatchedAccount testing");
	}
	

	@Test
	public void findAllOtherPendingAccountsTest() {
		assertEquals(userService.findAllOtherMatchedAccounts(userAccount).size(), 1);
		log.info("findAllOtherPendingAccount testing");
	}
	
	//Login tests
	@Test
	public void loginTest() {
		assertEquals(loginController.loginToAccount(logindto),userAccount);
		log.info("login testing");
	}
	


	@AfterAll
	public void DeleteAccount() {
		userService.deleteHBUserAccount(userAccount.getId());
	}

}
