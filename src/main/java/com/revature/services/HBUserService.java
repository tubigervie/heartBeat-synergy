package com.revature.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.revature.models.FilterMatchType;
import com.revature.models.HBMatch;
import com.revature.models.HBMatch.MatchResponse;
import com.revature.models.HBTopArtist;
import com.revature.models.HBTopGenre;
import com.revature.models.HBUserAccount;
import com.revature.models.HBUserImage;
import com.revature.repos.HBMatchDAO;
import com.revature.repos.HBTopArtistDAO;
import com.revature.repos.HBTopGenreDAO;
import com.revature.repos.HBUserDAO;
import com.revature.repos.HBUserImageDAO;
import com.revature.utils.FileUploadUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class HBUserService 
{

	public static Logger myLogger = LoggerFactory.getLogger("myLogger");

	@Autowired
	private HBUserDAO userDAO;
	
	@Autowired
	private HBTopArtistDAO artistDAO;
	
	@Autowired
	private HBTopGenreDAO genreDAO;
	
	@Autowired
	private HBUserImageDAO imageDAO;
	
	@Autowired
	private HBMatchDAO matchDAO;
	
	public HBUserService(HBUserDAO userDAO, HBTopArtistDAO artistDAO, HBTopGenreDAO genreDAO, HBUserImageDAO imageDAO, HBMatchDAO matchDAO)
	{
		this.userDAO = userDAO;
		this.artistDAO = artistDAO;
		this.genreDAO = genreDAO;
		this.imageDAO = imageDAO;
		this.matchDAO = matchDAO;
	}
	
	public boolean addGenre(HBTopGenre genre){
		try {
			genreDAO.save(genre);
			return true;
		}
		catch(IllegalArgumentException e){
			myLogger.info("in addGenre:service");
			myLogger.info(e.getMessage());
			return false;
		}
		
	}
	
	public boolean addOrUpdateMatch(HBMatch match){
		try {;
			matchDAO.save(match);
			return true;
		}
		catch(IllegalArgumentException e){
			myLogger.info("in addOrUpdateMatch:service");
			myLogger.info(e.getMessage());			
			return false;
		}
	}
	
	
	
	public HBUserImage storeImage(HBUserAccount user, MultipartFile file) throws IOException
	{
		HBUserImage image = new HBUserImage(file.getOriginalFilename(), file.getContentType(), user, FileUploadUtil.compressBytes(file.getBytes()));
		return imageDAO.save(image);
	}
	
	@Transactional
	public List<HBUserImage> getImagesByUser(HBUserAccount user)
	{
		List<HBUserImage> compressedImages = imageDAO.findByUser(user);
		List<HBUserImage> decompressedImages = new ArrayList<HBUserImage>();
		for(HBUserImage compressedImage : compressedImages)
		{
			HBUserImage decompressedImage = new HBUserImage(compressedImage.getId(), compressedImage.getName(), compressedImage.getType(), user, FileUploadUtil.decompressBytes(compressedImage.getPicByte()));
			decompressedImages.add(decompressedImage);
		}
		return decompressedImages;
	}
	
	@Transactional
	public void deleteUserImages(HBUserAccount user)
	{
		imageDAO.deleteByUser(user);
	}
	
	public boolean findGenreFromUser(HBUserAccount user, HBTopGenre genre)
	{
		try {
			HBTopGenre found = genreDAO.findGenreFromUser(genre.getGenre(), user).get();
			if(found != null) return true;
		}
		catch(NoSuchElementException e)
		{
			myLogger.info("in findGenreFromUser:service");
			myLogger.info(e.getMessage());
			return false;
		}
		return false;

	}
	
	public boolean addHBUserTopGenres(List<HBTopGenre> genres)
	{
		for(HBTopGenre genre : genres)
		{
			if(!addGenre(genre))
				return false;
		}
		return true;
	}
	
	public List<HBTopGenre> findTopGenresByUserId(int id) {
		HBUserAccount account = findAccountById(id);
		return genreDAO.findByUser(account);
	}
	
	@Transactional
	public void deleteHBUserTopGenres(HBUserAccount user)
	{
		genreDAO.deleteByUser(user);
	}
	
	@Transactional
	public List<HBUserAccount> findAllUserAccounts()
	{
		return userDAO.findAll();
	}
	
	public Set<HBUserAccount> findAllOtherUserAccountsOfSameGenres(HBUserAccount user)
	{
		Set<HBUserAccount> commonAccounts = new HashSet<HBUserAccount>();
		for(HBTopGenre genre : user.getTopGenres())
		{
			List<HBTopGenre> others = genreDAO.findOtherByGenre(genre.getGenre()).get();
			for(HBTopGenre otherGenre : others)
			{
				if(otherGenre.getUser() != user)
				{
					HBMatch match = findExistingMatchByCombination(user.getId(), otherGenre.getUser().getId());
					if((match == null && (user.getFilterType() == FilterMatchType.EVERYONE || otherGenre.getUser().getUserType() == user.getFilterType())) || (match != null && match.getMatchee() == otherGenre.getUser().getId() && match.getMatcheeResponse() == MatchResponse.PENDING))
						commonAccounts.add(otherGenre.getUser());
				}
			}
		}
		return commonAccounts;
	}
	
	public List<HBUserAccount> findAllOtherMatchedAccounts(HBUserAccount user)
	{
		List<HBUserAccount> accounts = new ArrayList<HBUserAccount>();
		List<HBMatch> matches = matchDAO.findByMatcherOrMatchee(user.getId());
		for(HBMatch match : matches)
		{
			if(match.getMatcheeResponse() == match.getMatcherResponse() && match.getMatcheeResponse() == MatchResponse.ACCEPT)
			{
				if(match.getMatchee() != user.getId())
					accounts.add(userDAO.findById(match.getMatchee()).get());
				else
					accounts.add(userDAO.findById(match.getMatcher()).get());
			}
		}
		return accounts;
	}
	
	public List<HBUserAccount> findAllOtherPendingAccounts(HBUserAccount user)
	{
		List<HBUserAccount> accounts = new ArrayList<HBUserAccount>();
		List<HBMatch> matches = matchDAO.findByMatcherOrMatchee(user.getId());
		for(HBMatch match : matches)
		{
			if(match.getMatcheeResponse() == MatchResponse.PENDING || match.getMatcherResponse() == MatchResponse.PENDING)
			{
				if(match.getMatchee() != user.getId())
					accounts.add(userDAO.findById(match.getMatchee()).get());
				else
					accounts.add(userDAO.findById(match.getMatcher()).get());
			}
		}
		return accounts;
	}
	
	public HBMatch findExistingMatchByCombination(int user, int other)
	{
		HBMatch match = matchDAO.findByMatchCombination(user, other);
		return match;
	}
	
	
	public HBUserAccount findAccountById(int id)
	{
		try {
			return userDAO.findById(id).get();
		}
		catch(NoSuchElementException e)
		{
			myLogger.info("in findAccountById:service");
			myLogger.info(e.getMessage());
			return null;
		}
	}
	
	public HBUserAccount findAccountByUsername(String username)
	{
		try {
			return userDAO.findByUsernameIgnoreCase(username);
		}
		catch(NoSuchElementException e)
		{
			myLogger.info("in findAccountByUsername:service");
			myLogger.info(e.getMessage());
			return null;
		}
	}
	
	public HBUserAccount addOrUpdateHBUserAccount(HBUserAccount account)
	{
		try {
			if(account.getFilterType() == null)
				account.setFilterType(FilterMatchType.EVERYONE);
			if(account.getUserType() == null)
				account.setUserType(FilterMatchType.EVERYONE);
			HBUserAccount updatedAccount = userDAO.save(account);
			return updatedAccount;
		}
		catch(IllegalArgumentException e)
		{
			myLogger.info("in addOrUpdateHBUserAccount:service");
			myLogger.info(e.getMessage());
			return null;
		}

	}
	
	public boolean addOrUpdateHBUserTopArtist(HBTopArtist artist)
	{
		try
		{
			artistDAO.save(artist);
			return true;
		}
		catch(IllegalArgumentException e)
		{
			myLogger.info("in addOrUpdateHBUserTopArtist:service");
			myLogger.info(e.getMessage());
			return false;
		}
	}
	
	@Transactional
	public boolean deleteHBUserTopArtists(HBUserAccount user)
	{
		try {
			artistDAO.deleteByUser(user);
			return true;
		}catch(IllegalArgumentException e) {
			return false;
		}
	}
	
	@Transactional
	public boolean deleteHBTopArtist(HBTopArtist artist)
	{
		try {
			artistDAO.delete(artist);
			return true;
		}catch(IllegalArgumentException e) {
			return false;
		}
	}
	
	public boolean addHBUserTopArtists(List<HBTopArtist> artists)
	{
		for(HBTopArtist artist : artists)
		{
			if(!addOrUpdateHBUserTopArtist(artist))
				return false;
		}
		return true;
	}
	
	public List<HBTopArtist> findTopArtistsByUserID(int id)
	{
		HBUserAccount account = findAccountById(id);
		return artistDAO.findByUser(account);
	}
	
	public HBTopArtist findTopArtistByID(int id)
	{
		try {
			return artistDAO.findById(id).get();
		}
		catch(NoSuchElementException e)
		{
			return null;
		}
	}
	
	public boolean deleteHBUserAccount(int id)
	{
		try {
			HBUserAccount account = findAccountById(id);
			userDAO.delete(account);
			return true;
		}
		catch(IllegalArgumentException e)
		{
			myLogger.info("in deleteHBUserAccount:service");
			myLogger.info(e.getMessage());
			return false;
		}
	}
	
}
