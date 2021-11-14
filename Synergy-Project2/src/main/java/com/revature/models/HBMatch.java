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
public class HBMatch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn
	@JsonBackReference(value="matcher-ref")
	@NotNull
	private HBUserAccount matcher;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn
	@JsonBackReference(value="matchee-ref")
	@NotNull
	private HBUserAccount matchee;
	
	private boolean matcherResponse;
	
	private boolean matcheeResponse;

	public HBMatch(int id, HBUserAccount matcher, HBUserAccount matchee, boolean matcherResponse,
			boolean matcheeResponse) {
		super();
		this.id = id;
		this.matcher = matcher;
		this.matchee = matchee;
		this.matcherResponse = matcherResponse;
		this.matcheeResponse = matcheeResponse;
	}

	public HBMatch(HBUserAccount matcher, HBUserAccount matchee, boolean matcherResponse, boolean matcheeResponse) {
		super();
		this.matcher = matcher;
		this.matchee = matchee;
		this.matcherResponse = matcherResponse;
		this.matcheeResponse = matcheeResponse;
	}
	
	public HBMatch()
	{
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HBUserAccount getMatcher() {
		return matcher;
	}

	public void setMatcher(HBUserAccount matcher) {
		this.matcher = matcher;
	}

	public HBUserAccount getMatchee() {
		return matchee;
	}

	public void setMatchee(HBUserAccount matchee) {
		this.matchee = matchee;
	}

	public boolean isMatcherResponse() {
		return matcherResponse;
	}

	public void setMatcherResponse(boolean matcherResponse) {
		this.matcherResponse = matcherResponse;
	}

	public boolean isMatcheeResponse() {
		return matcheeResponse;
	}

	public void setMatcheeResponse(boolean matcheeResponse) {
		this.matcheeResponse = matcheeResponse;
	}
	
}
