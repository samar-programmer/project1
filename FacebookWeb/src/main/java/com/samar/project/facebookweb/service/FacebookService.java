package com.samar.project.facebookweb.service;

import java.util.List;

import com.samar.project.facebookweb.entity.FacebookUser;
import com.samar.project.facebookweb.entity.TimeLine;
import com.samar.project.facebookweb.interfaces.FacebookDAOInterface;
import com.samar.project.facebookweb.interfaces.FacebookServiceInterface;
import com.samar.project.facebookweb.utility.DAOFactory;

public class FacebookService implements FacebookServiceInterface {
	private FacebookDAOInterface facebookDAOInterface;
	public FacebookService() {
		facebookDAOInterface=DAOFactory.createObject();
	}
	@Override
	public int createProfileService(FacebookUser facebookUser) {
		return facebookDAOInterface.createProfileDAO(facebookUser);
	}

	@Override
	public int loginProfileService(FacebookUser facebookUser) {
		return facebookDAOInterface.loginProfileDAO(facebookUser);
	}

	@Override
	public List<TimeLine> timeLineService(FacebookUser facebookUser) {
		return facebookDAOInterface.timeLineDAO(facebookUser);
	}
	@Override
	public FacebookUser viewProfileService(String email, String password) {
		return facebookDAOInterface.viewProfileDAO(email, password);
	}
	@Override
	public int editProfileService(FacebookUser facebookUser) {
		return facebookDAOInterface.editProfileDAO(facebookUser);
	}
	@Override
	public int deleteProfileService(String email) {
		return facebookDAOInterface.deleteProfileDAO(email);
	}
	@Override
	public List<FacebookUser> searchProfileService(String searchValue) {
		return facebookDAOInterface.searchProfileDAO(searchValue);
	}
	@Override
	public String loginProfileStatusService(String email) {
		return facebookDAOInterface.loginProfileStatusDAO(email);
	}
	@Override
	public int uploadPhotoService(String retrival_name_of_image, String email) {
		return facebookDAOInterface.uploadPhotoDAO(retrival_name_of_image,email);
	}
	@Override
	public String getProfilePhotoService(String email) {
		return facebookDAOInterface.getProfilePhotoDAO(email);
	}
	@Override
	public List<FacebookUser> viewAllProfileService() {
		return facebookDAOInterface.viewAllProfileDAO();
	}
	@Override
	public int blockUserService(String email) {
		return facebookDAOInterface.blockUserDAO(email);
	}
	@Override
	public int unBlockUserService(String email) {
		return facebookDAOInterface.unBlockUserDAO(email);
	}
	@Override
	public int createPostService(String retrival_name_of_image, String email, String postMessage,
			FacebookUser facebookUserDetail) {
		
		return facebookDAOInterface.createPostService(retrival_name_of_image, email, postMessage, facebookUserDetail);
	}
	@Override
	public int deleteProfileTimeLine(String tid) {
		return facebookDAOInterface.deleteProfileTimeLine(tid);
	}
	@Override
	public int uploadAddService(String retrival_name_of_image, String email) {
		return facebookDAOInterface.uploadAddDAO(retrival_name_of_image, email);
	}

}
