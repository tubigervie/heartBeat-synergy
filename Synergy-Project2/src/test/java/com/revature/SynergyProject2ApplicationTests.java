package com.revature;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.revature.models.HBMatch;
import com.revature.models.HBTopArtist;
import com.revature.models.HBTopGenre;
import com.revature.models.HBUserAccount;
import com.revature.repos.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.services.HBUserService;


@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
class SynergyProject2ApplicationTests {
	
	
	@Autowired
	private HBUserService userService;
	
	//User tests
	
	private static Logger log = LoggerFactory.getLogger(SynergyProject2ApplicationTests.class);
	HBUserAccount userAccount = new HBUserAccount("hbUserTester", "testpass", "testname", "testlast", 0, "testDesc", "testList", "testAnthem", null, null, "EVERYONE", "EVERYONE");
	HBTopArtist topArtist = new HBTopArtist(0, "testArtistId", "testArtistName", "test", null);
	HBTopGenre topGenre = new HBTopGenre("test");
	HBMatch match = new HBMatch(userAccount, userAccount, true, true);
	
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
	
	@AfterAll
	public void DeleteAccount() {
		userService.deleteHBUserAccount(userAccount.getId());
	}
}
