package com.samar.project.facebookweb.utility;

import com.samar.project.facebookweb.dao.FacebookDAO;
import com.samar.project.facebookweb.interfaces.FacebookDAOInterface;

public class DAOFactory {

	public static FacebookDAOInterface createObject() {
		// TODO Auto-generated method stub
		return new FacebookDAO();
	}

}
