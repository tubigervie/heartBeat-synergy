package com.revature.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.revature.models.HBTopArtist;
import com.revature.models.HBUserAccount;
import com.revature.repos.HBTopArtistDAO;
import com.revature.repos.HBUserDAO;

@Service
public class HBUserService 
{
	@Autowired
	private HBUserDAO userDAO;
	
	@Autowired
	private HBTopArtistDAO artistDAO;
	
	public HBUserService(HBUserDAO userDAO, HBTopArtistDAO artistDAO)
	{
		this.userDAO = userDAO;
		this.artistDAO = artistDAO;
	}
	
	public List<HBUserAccount> findAllUserAccounts()
	{
		return userDAO.findAll();
	}
	
	public HBUserAccount findAccountById(int id)
	{
		try {
			return userDAO.findById(id).get();
		}
		catch(NoSuchElementException e)
		{
			return null;
		}
	}
	
	public HBUserAccount findAccountByUsername(String username)
	{
		try {
			return userDAO.findAccountByUsername(username).get();
		}
		catch(NoSuchElementException e)
		{
			return null;
		}
	}
	
	public boolean addOrUpdateHBUserAccount(HBUserAccount account)
	{
		try {
			userDAO.save(account);
			return true;
		}
		catch(IllegalArgumentException e)
		{
			return false;
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
			return false;
		}
	}
	
	@Transactional
	public boolean deleteHBUserTopArtists(HBUserAccount user)
	{
		artistDAO.deleteByUser(user);
		return true;
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
	
	public boolean deleteHBUserAccount(int id)
	{
		try {
			HBUserAccount account = findAccountById(id);
			userDAO.delete(account);
			return true;
		}
		catch(IllegalArgumentException e)
		{
			return false;
		}
	}
	
}
