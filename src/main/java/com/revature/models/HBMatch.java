package com.revature.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import com.sun.istack.NotNull;

@Entity
public class HBMatch {
	public enum MatchResponse {ACCEPT, DECLINE, PENDING};
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	private int matcherId;
	
	@NotNull
	private int matcheeId;
	
	@Enumerated(EnumType.STRING)
	private MatchResponse matcheeResponse;
	
	@Enumerated(EnumType.STRING)
	private MatchResponse matcherResponse;

	public HBMatch(int id, int matcher, int matchee, String matcherResponse,
			String matcheeResponse) {
		super();
		this.id = id;
		this.matcherId = matcher;
		this.matcheeId = matchee;
		this.matcherResponse = MatchResponse.valueOf(matcherResponse);
		this.matcheeResponse = MatchResponse.valueOf(matcheeResponse);
	}

	public HBMatch(int matcher, int matchee, String matcherResponse, String matcheeResponse) {
		super();
		this.matcherId = matcher;
		this.matcheeId = matchee;
		this.matcherResponse = MatchResponse.valueOf(matcherResponse);
		this.matcheeResponse = MatchResponse.valueOf(matcheeResponse);
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

	public int getMatcher() {
		return matcherId;
	}

	public void setMatcher(int matcher) {
		this.matcherId = matcher;
	}

	public int getMatchee() {
		return matcheeId;
	}

	public void setMatchee(int matchee) {
		this.matcheeId = matchee;
	}

	public MatchResponse getMatcherResponse() {
		return matcherResponse;
	}

	public void setMatcherResponse(MatchResponse matcherResponse) {
		this.matcherResponse = matcherResponse;
	}

	public MatchResponse getMatcheeResponse() {
		return matcheeResponse;
	}

	public void setMatcheeResponse(MatchResponse matcheeResponse) {
		this.matcheeResponse = matcheeResponse;
	}
	
}
