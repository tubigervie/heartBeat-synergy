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
public class HBTopGenre 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	@NotNull
	private String genre;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn
	@JsonBackReference
	@NotNull
	private HBUserAccount user;

	public HBTopGenre(int id, String genre, HBUserAccount user) {
		super();
		this.id = id;
		this.genre = genre;
		this.user = user;
	}

	public HBTopGenre(String genre, HBUserAccount user) {
		super();
		this.genre = genre;
		this.user = user;
	}
	
	public HBTopGenre(String genre) {
		super();
		this.genre = genre;
	}

	public HBTopGenre() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public HBUserAccount getUser() {
		return user;
	}

	public void setUser(HBUserAccount user) {
		this.user = user;
	}
	
}
