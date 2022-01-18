package com.samar.project.facebookweb.entity;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "FACEBOOK_USER_DETAILS")
public class FacebookUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int user_id;

	private String name;
	private String password;
	@Column(unique = true)
	private String email;
	private String gender;
	@Column(name = "DOB")
	private String age;
	private String address;
	private String status;
	private String imageName;
	private String addName;

	@OneToMany(mappedBy = "facebookuser")
	private List<TimeLine> timelines;

	public List<TimeLine> getTimelines() {
		return timelines;
	}

	public void setTimelines(List<TimeLine> timelines) {
		this.timelines = timelines;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getAddName() {
		return addName;
	}

	public void setAddName(String addName) {
		this.addName = addName;
	}
}
