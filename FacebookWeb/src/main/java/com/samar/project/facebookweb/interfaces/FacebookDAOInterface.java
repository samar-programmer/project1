package com.samar.project.facebookweb.interfaces;

import java.util.List;

import com.samar.project.facebookweb.entity.FacebookUser;
import com.samar.project.facebookweb.entity.TimeLine;

public interface FacebookDAOInterface {

	int createProfileDAO(FacebookUser facebookUser);

	int loginProfileDAO(FacebookUser facebookUser);

	List<TimeLine> timeLineDAO(FacebookUser facebookUser);

	FacebookUser viewProfileDAO(String email, String password);

	int editProfileDAO(FacebookUser facebookUser);

	int deleteProfileDAO(String email);

	List<FacebookUser> searchProfileDAO(String searchValue);

	String loginProfileStatusDAO(String email);

	int uploadPhotoDAO(String retrival_name_of_image, String string);

	String getProfilePhotoDAO(String email);

	List<FacebookUser> viewAllProfileDAO();

	int blockUserDAO(String email);

	int unBlockUserDAO(String email);

	int createPostService(String retrival_name_of_image, String email, String postMessage,
			FacebookUser facebookUserDetail);

	int deleteProfileTimeLine(String tid);

	int uploadAddDAO(String retrival_name_of_image, String email);

}
