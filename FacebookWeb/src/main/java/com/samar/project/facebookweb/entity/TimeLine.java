package com.samar.project.facebookweb.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "FACEBOOK_USER_TIMELINE")
public class TimeLine {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long timelineid;
	
	
	private String sender_id;
	private String receiver_Id;
	private String message;
	private String messagelike;
	private String dislike;
	private String rtime;
	private String imageLocation;
	private String email;
	
	@ManyToOne
	private FacebookUser facebookuser;

	public long getTimelineid() {
		return timelineid;
	}
	

	public String getMessagelike() {
		return messagelike;
	}


	public void setMessagelike(String messagelike) {
		this.messagelike = messagelike;
	}


	public void setTimelineid(long timelineid) {
		this.timelineid = timelineid;
	}

	public String getSender_id() {
		return sender_id;
	}

	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}

	public String getReceiver_Id() {
		return receiver_Id;
	}

	public void setReceiver_Id(String receiver_Id) {
		this.receiver_Id = receiver_Id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

	public String getDislike() {
		return dislike;
	}

	public void setDislike(String dislike) {
		this.dislike = dislike;
	}

	public String getRtime() {
		return rtime;
	}

	public void setRtime(String rtime) {
		this.rtime = rtime;
	}

	public FacebookUser getFacebookuser() {
		return facebookuser;
	}

	public void setFacebookuser(FacebookUser facebookuser) {
		this.facebookuser = facebookuser;
	}


	public String getImageLocation() {
		return imageLocation;
	}


	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	

}
