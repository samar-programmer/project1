package com.samar.project.facebookweb.interfaces;

import java.util.List;

import com.samar.project.facebookweb.entity.FacebookUser;
import com.samar.project.facebookweb.entity.TimeLine;

public interface FacebookServiceInterface {

	int createProfileService(FacebookUser facebookUser);

	int loginProfileService(FacebookUser facebookUser);

	List<TimeLine> timeLineService(FacebookUser fb);

	FacebookUser viewProfileService(String email, String password);

	int editProfileService(FacebookUser facebookUser);

	int deleteProfileService(String email);

	List<FacebookUser> searchProfileService(String string);

	String loginProfileStatusService(String email);

	int uploadPhotoService(String retrival_name_of_image, String email);

	String getProfilePhotoService(String email);

	List<FacebookUser> viewAllProfileService();

	int blockUserService(String parameter);

	int unBlockUserService(String parameter);

	int createPostService(String retrival_name_of_image, String string, String postMessage,
			FacebookUser facebookUserDetail);

	int deleteProfileTimeLine(String tid);

	int uploadAddService(String retrival_name_of_image, String string);

}
