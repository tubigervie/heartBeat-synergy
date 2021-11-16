package com.revature.models;

import javax.persistence.Column;
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
public class HBUserImage 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String type;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn
	@JsonBackReference
	@NotNull
	private HBUserAccount user;
	
	@Column(name="picByte", length=1000)
	private byte[] picByte;

	public HBUserImage(String name, String type, HBUserAccount user, byte[] picByte) {
		super();
		this.name = name;
		this.type = type;
		this.user = user;
		this.picByte = picByte;
	}

	public HBUserImage(int id, String name, String type, HBUserAccount user, byte[] picByte) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.user = user;
		this.picByte = picByte;
	}

	public HBUserImage() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public HBUserAccount getUser() {
		return user;
	}

	public void setUser(HBUserAccount user) {
		this.user = user;
	}

	public byte[] getPicByte() {
		return picByte;
	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
