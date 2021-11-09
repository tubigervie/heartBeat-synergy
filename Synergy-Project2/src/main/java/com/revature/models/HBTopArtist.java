package com.revature.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;

@Entity
public class HBTopArtist 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	@NotNull
	private String artistId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn
	@JsonBackReference
	@NotNull
	private HBUserAccount user;
	
	public HBTopArtist(int id, String artistId, HBUserAccount user) {
		super();
		this.id = id;
		this.artistId = artistId;
		this.user = user;
	}

	public HBTopArtist(String artistId, HBUserAccount user) {
		super();
		this.artistId = artistId;
		this.user = user;
	}

	public HBTopArtist() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getArtistId() {
		return artistId;
	}

	public void setArtistId(String artistId) {
		this.artistId = artistId;
	}

	public HBUserAccount getUser() {
		return user;
	}

	public void setUser(HBUserAccount user) {
		this.user = user;
	}
	
}
