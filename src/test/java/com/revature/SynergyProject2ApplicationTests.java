package com.revature;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.revature.controllers.HBLoginController;
import com.revature.models.HBLoginDTO;
import com.revature.models.HBMatch;
import com.revature.models.HBTopArtist;
import com.revature.models.HBTopGenre;
import com.revature.models.HBUserAccount;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.revature.services.HBUserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
class SynergyProject2ApplicationTests {

	//17 out of 22 methods have tests
	
	@Autowired
	private HBUserService userService;
	@Autowired
	private HBLoginController loginController;
	

	//User tests
	
	// private static Logger log = LoggerFactory.getLogger(SynergyProject2ApplicationTests.class);
	public static Logger myLogger = LoggerFactory.getLogger("myLoggerTest");

	List <HBTopArtist> topArtistList = new ArrayList<HBTopArtist>();
	List <HBTopGenre> topGenreList = new ArrayList<HBTopGenre>();

	HBUserAccount userAccount1 = new HBUserAccount("hbUserTester1", "testpass", "testname", "testlast", 0,
	"testDesc", "testList", "testAnthem", topArtistList, topGenreList, "EVERYONE", "EVERYONE");
	HBUserAccount userAccount2 = new HBUserAccount("hbUserTester2", "testpass", "testname", "testlast", 0,
	"testDesc", "testList", "testAnthem", topArtistList, topGenreList, "EVERYONE", "EVERYONE");
	HBUserAccount userAccount3 = new HBUserAccount("hbUserTester3", "testpass", "testname", "testlast", 0,
	"testDesc", "testList", "testAnthem", topArtistList, topGenreList, "EVERYONE", "EVERYONE");
	HBUserAccount userAccount4 = new HBUserAccount("hbUserTester4", "testpass", "testname", "testlast", 0,
	"testDesc", "testList", "testAnthem", topArtistList, topGenreList, "EVERYONE", "EVERYONE");

	HBTopArtist topArtist = new HBTopArtist(0, "testArtistId", "testArtistName", "test", null);
	HBTopGenre topGenre = new HBTopGenre("test");
	HBLoginDTO logindto = new HBLoginDTO("hbUserTester", "testpass");


	@Test
	@BeforeAll
	public void addAccount(){
		myLogger.info("NEW TEST:");
		HBUserAccount test1 = userService.addOrUpdateHBUserAccount(userAccount1);
		assertEquals(test1,userAccount1);
		HBUserAccount test2 = userService.addOrUpdateHBUserAccount(userAccount2);
		assertEquals(test2,userAccount2);
		
		HBUserAccount test3 = userService.addOrUpdateHBUserAccount(userAccount3);
		assertEquals(test3,userAccount3);
		HBUserAccount test4 = userService.addOrUpdateHBUserAccount(userAccount4);
		assertEquals(test4,userAccount4);

		myLogger.info("User Id: " + String.valueOf(userAccount1.getId()));

	}
	
	@Test
	public void getAllAccounts() {
		myLogger.info("getAllAccounts testing");
		List<HBUserAccount> list = userService.findAllUserAccounts();
		assertNotEquals(list, 0);
		myLogger.info("List size: " + String.valueOf(list.size()));
	}
	
	@Test
	public void getAccountById() {
		myLogger.info("getAccountById testing");
		HBUserAccount test = userService.findAccountById(userAccount1.getId());
		assertEquals(test, userAccount1);
	}
	
	@Test 
	public void getAccountByUsername() {
		myLogger.info("getAccountByUsername testing");
		HBUserAccount test = userService.findAccountByUsername(userAccount1.getUsername());
		assertEquals(test, userAccount1);
	}
	
	//Artist tests
	@Test
	public void addTopArtistTest(){
		myLogger.info("addTopArtist testing");
		List<HBTopArtist> list = new ArrayList<HBTopArtist>();
		list.add(topArtist);
		assertTrue(userService.addHBUserTopArtists(list));
	}
	
	@Test
	public void addHBTopArtistTest() {
		myLogger.info("addHBTopArtist testing");
		List<HBTopArtist> list = new ArrayList<HBTopArtist>();
		list.add(topArtist);
		assertTrue(userService.addHBUserTopArtists(list));
		myLogger.info("List: " +list.toString());
	}
	
	@Test
	public void deleteHBTopArtistTest() {
		myLogger.info("deleteTopArtis testing");
		assertTrue(userService.deleteHBUserTopArtists(userAccount1));	
	}
	
	@Test
	public void findTopArtistByUserIdTest(){
		myLogger.info("findTopArtist testing");
		List<HBTopArtist> list = new ArrayList<HBTopArtist>();
		list = userService.findTopArtistsByUserID(userAccount1.getId());
		assertEquals(list.size(), 0);
	}
	
	//Genre tests
	@Test
	public void addGenreTest() {
		myLogger.info("addGenre testing");
		assertTrue(userService.addGenre(topGenre));
	}
	
	@Test
	public void addHBTopGenres() {
		myLogger.info("addHBTopGenre testing");
		List<HBTopGenre> list = new ArrayList<HBTopGenre>();
		list.add(topGenre);
		assertTrue(userService.addHBUserTopGenres(list));
		myLogger.info("Genre list: " +list.toString());
	}
	
	@Test
	public void findTopGenresByUserIdTest() {
		myLogger.info("findTopGenresByUser testing");
		List<HBTopGenre> list = new ArrayList<HBTopGenre>();
		list = userService.findTopGenresByUserId(userAccount1.getId());
		assertEquals(list.size(), 0);
	}
	
	
	//Match tests
	@Test
	public void addMatch() {
		myLogger.info("addMatch testing");
		HBMatch match = new HBMatch(userAccount1.getId(), userAccount2.getId(), "ACCEPT", "ACCEPT");
	 	assertTrue(userService.addOrUpdateMatch(match));
	 	myLogger.info("Match Id: " + String.valueOf(match.getId()));
	}
	

	@Test
	public void findMatchByUsers(){
		myLogger.info("In findMatchByUsers: testing");
		HBMatch match = new HBMatch(userAccount1.getId(), userAccount2.getId(), "ACCEPT", "ACCEPT");
		assertTrue(userService.addOrUpdateMatch(match));
		match = userService.findExistingMatchByCombination(userAccount1.getId(), userAccount2.getId());
		assertEquals(match.getMatcher(), userAccount1.getId());
		assertEquals(match.getMatchee(), userAccount2.getId());
	}
	
	@Test
	public void findAllOtherMatchedAccountsTest() {
		myLogger.info("findAllOtherMatchedAccounts testing");
		List<HBUserAccount> _list = new ArrayList<HBUserAccount>();

		HBMatch match1 = new HBMatch(userAccount1.getId(), userAccount2.getId(), "ACCEPT", "ACCEPT");
		HBMatch match2 = new HBMatch(userAccount1.getId(), userAccount3.getId(), "ACCEPT", "ACCEPT");

		assertTrue(userService.addOrUpdateMatch(match1));
		assertTrue(userService.addOrUpdateMatch(match2));

		_list = userService.findAllOtherMatchedAccounts(userAccount1);

		for (HBUserAccount account : _list) {
			myLogger.info(account.toString());
		}

		assertEquals(_list.get(0), userAccount2);
		assertEquals(_list.get(1), userAccount3);
	}
	

	@Test
	public void findAllOtherPendingAccountsTest() {
		myLogger.info("findAllOtherPendingAccount testing");

		List<HBUserAccount> allUsers = userService.findAllUserAccounts();
		List<HBUserAccount> pendingAccounts = new ArrayList<HBUserAccount>();

		pendingAccounts = userService.findAllOtherPendingAccounts(userAccount1);

		assertEquals(pendingAccounts.get(0), allUsers.get(1));
		assertEquals(pendingAccounts.get(1), allUsers.get(2));
		assertEquals(pendingAccounts.get(2), allUsers.get(3));

	}
	
	//Login tests
	@Test
	public void loginTest() {
		myLogger.info("login testing");
		assertEquals(loginController.loginToAccount(logindto),userAccount1);
	}
	


	@AfterAll
	public void DeleteAccount() {
		userService.deleteHBUserAccount(userAccount1.getId());
		userService.deleteHBUserAccount(userAccount2.getId());
		userService.deleteHBUserAccount(userAccount3.getId());
		userService.deleteHBUserAccount(userAccount4.getId());
	}

}
