package com.revature.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;

@Entity
public class HBUserAccount 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	@NotNull
	private String username;
	
	@NotNull
	private String password;
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	private String profileDescription;
	
	private String playlist;
	
	private String anthem;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
	@JsonManagedReference
	private List<HBTopArtist> topArtists;
	
	@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
	@JsonManagedReference
	private List<HBTopGenre> topGenres;

	public HBUserAccount(int id, String username, String password, String firstName, String lastName,
			String profileDescription, String playlist, String anthem, List<HBTopArtist> topArtists,
			List<HBTopGenre> topGenres) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.profileDescription = profileDescription;
		this.playlist = playlist;
		this.anthem = anthem;
		this.topArtists = topArtists;
		this.topGenres = topGenres;
	}	

	public HBUserAccount(String username, String password, String firstName, String lastName, String profileDescription,
			String playlist, String anthem, List<HBTopArtist> topArtists, List<HBTopGenre> topGenres) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.profileDescription = profileDescription;
		this.playlist = playlist;
		this.anthem = anthem;
		this.topArtists = topArtists;
		this.topGenres = topGenres;
	}

	public HBUserAccount(String username, String password, String firstName, String lastName) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public HBUserAccount() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getProfileDescription() {
		return profileDescription;
	}

	public void setProfileDescription(String profileDescription) {
		this.profileDescription = profileDescription;
	}

	public String getPlaylist() {
		return playlist;
	}

	public void setPlaylist(String playlist) {
		this.playlist = playlist;
	}

	public String getAnthem() {
		return anthem;
	}

	public void setAnthem(String anthem) {
		this.anthem = anthem;
	}

	public List<HBTopArtist> getTopArtists() {
		return topArtists;
	}

	public void setTopArtists(List<HBTopArtist> topArtists) {
		this.topArtists = topArtists;
	}

	public List<HBTopGenre> getTopGenres() {
		return topGenres;
	}

	public void setTopGenres(List<HBTopGenre> topGenres) {
		this.topGenres = topGenres;
	}

	@Override
	public String toString() {
		return "HBUserAccount [id=" + id + ", username=" + username + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", profileDescription=" + profileDescription + ", playlist="
				+ playlist + ", anthem=" + anthem + ", topArtists=" + topArtists + ", topGenres=" + topGenres + "]";
	}
	
}
