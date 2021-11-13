package com.revature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	HBUserAccount userAccount = new HBUserAccount(0, "hbUserTester", "testpass", "testname", "testlast", 0, "testDesc", "testList", "testAnthem", null, null, "EVERYONE", "EVERYONE");
	HBTopArtist topArtist = new HBTopArtist(0, "testArtistId", "testArtistName", "test", null);
	HBTopGenre topGenre = new HBTopGenre("test");
	
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
		log.warn(test.toString());
		log.warn(userAccount.toString());
		assertEquals(test, userAccount);
	}
	
	@Test 
	public void getAccountByUsername() {
		HBUserAccount test = userService.findAccountByUsername(userAccount.getUsername());
		log.warn(test.toString());
		log.warn(userAccount.toString());
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
	public void findTopArtistByUserIdTest(){
		List<HBTopArtist> list = new ArrayList<HBTopArtist>();
		list = userService.findTopArtistsByUserID(userAccount.getId());
		assertEquals(list, 0);
	}
	
	//Genre tests
	@Test
	public void addGenreTest() {
		assertTrue(userService.addGenre(topGenre));
	}
	

	
	@AfterAll
	public void DeleteAccount() {
		userService.deleteHBUserAccount(userAccount.getId());
	}
}
