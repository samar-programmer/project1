package com.samar.project.facebookweb.utility;

import com.samar.project.facebookweb.interfaces.FacebookServiceInterface;
import com.samar.project.facebookweb.service.FacebookService;

public class ServiceFactory {
	public static FacebookServiceInterface createObject() {
		return new FacebookService();
	}

}
