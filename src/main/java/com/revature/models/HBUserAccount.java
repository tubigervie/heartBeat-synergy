package com.revature.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;

@Entity
public class HBUserAccount 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	@Column(unique=true)
	@NotNull
	private String username;
	
	@NotNull
	private String password;
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	private int age;
	
	private String profileDescription;
	
	private String playlist;
	
	private String anthem;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="user")
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonManagedReference
	private List<HBTopArtist> topArtists;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="user")
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonManagedReference
	private List<HBTopGenre> topGenres;
	
	private FilterMatchType filterType;
	
	private FilterMatchType userType;

	public HBUserAccount(int id, String username, String password, String firstName, String lastName,
			int age, String profileDescription, String playlist, String anthem, List<HBTopArtist> topArtists,
			List<HBTopGenre> topGenres, String filterType, String userType) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.profileDescription = profileDescription;
		this.playlist = playlist;
		this.anthem = anthem;
		this.topArtists = topArtists;
		this.topGenres = topGenres;
		this.filterType = FilterMatchType.valueOf(filterType);
		this.userType = FilterMatchType.valueOf(userType);
	}	

	public HBUserAccount(String username, String password, String firstName, String lastName, int age, String profileDescription,
			String playlist, String anthem, List<HBTopArtist> topArtists, List<HBTopGenre> topGenres, String filterType, String userType) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		this.profileDescription = profileDescription;
		this.playlist = playlist;
		this.anthem = anthem;
		this.topArtists = topArtists;
		this.topGenres = topGenres;
		this.filterType = FilterMatchType.valueOf(filterType);
		this.userType = FilterMatchType.valueOf(userType);
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

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
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
	
	

	public FilterMatchType getFilterType() {
		return filterType;
	}

	public void setFilterType(FilterMatchType filterType) {
		this.filterType = filterType;
	}

	public FilterMatchType getUserType() {
		return userType;
	}

	public void setUserType(FilterMatchType userType) {
		this.userType = userType;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + age;
		result = prime * result + ((anthem == null) ? 0 : anthem.hashCode());
		result = prime * result + ((filterType == null) ? 0 : filterType.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((playlist == null) ? 0 : playlist.hashCode());
		result = prime * result + ((profileDescription == null) ? 0 : profileDescription.hashCode());
		result = prime * result + ((topArtists == null) ? 0 : topArtists.hashCode());
		result = prime * result + ((topGenres == null) ? 0 : topGenres.hashCode());
		result = prime * result + ((userType == null) ? 0 : userType.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HBUserAccount other = (HBUserAccount) obj;
		if (age != other.age)
			return false;
		if (anthem == null) {
			if (other.anthem != null)
				return false;
		} else if (!anthem.equals(other.anthem))
			return false;
		if (filterType != other.filterType)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (playlist == null) {
			if (other.playlist != null)
				return false;
		} else if (!playlist.equals(other.playlist))
			return false;
		if (profileDescription == null) {
			if (other.profileDescription != null)
				return false;
		} else if (!profileDescription.equals(other.profileDescription))
			return false;
		if (userType != other.userType)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HBUserAccount [id=" + id + ", username=" + username + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", profileDescription=" + profileDescription + ", playlist="
				+ playlist + ", anthem=" + anthem + ", topArtists=" + topArtists + ", topGenres=" + topGenres + "]";
	}
	
	
	
}
