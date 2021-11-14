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
	
	@NotNull
	private String artistName;
	
	@NotNull
	private String artistImage;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn
	@JsonBackReference
	@NotNull
	private HBUserAccount user;
	
	public HBTopArtist(int id, String artistId, String artistName, String artistImage, HBUserAccount user) {
		super();
		this.id = id;
		this.artistId = artistId;
		this.artistName = artistName;
		this.artistImage = artistImage;
		this.user = user;
	}
	
	public HBTopArtist(int id, String artistId, String artistName, String artistImage) {
		super();
		this.id = id;
		this.artistId = artistId;
		this.artistName = artistName;
		this.artistImage = artistImage;
	}
	
	public HBTopArtist(String artistId, String artistName, String artistImage) {
		super();
		this.artistId = artistId;
		this.artistName = artistName;
		this.artistImage = artistImage;
	}

	public String getArtistName() {
		return artistName;
	}

	public void setArtistName(String artistName) {
		this.artistName = artistName;
	}

	public String getArtistImage() {
		return artistImage;
	}

	public void setArtistImage(String artistImage) {
		this.artistImage = artistImage;
	}

	public HBTopArtist() {
		super();
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
