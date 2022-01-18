package com.samar.project.facebookweb.dao;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.annotation.MultipartConfig;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.samar.project.facebookweb.entity.FacebookUser;
import com.samar.project.facebookweb.entity.TimeLine;
import com.samar.project.facebookweb.interfaces.FacebookDAOInterface;

public class FacebookDAO implements FacebookDAOInterface {

	private SessionFactory sessionFactory;

	public FacebookDAO() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}

	@Override
	public int createProfileDAO(FacebookUser facebookUser) {
		int result = 0;
		Session session = sessionFactory.openSession();
		EntityTransaction entityTransaction = session.getTransaction();
		entityTransaction.begin();
		session.save(facebookUser);
		entityTransaction.commit();
		result++;
		return result;
	}

	@Override
	public int loginProfileDAO(FacebookUser facebookUser) {
		int result = 0;
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(
				"from com.samar.project.facebookweb.entity.FacebookUser f where f.email=:email and f.password=:password");
		query.setParameter("email", facebookUser.getEmail());
		query.setParameter("password", facebookUser.getPassword());
		FacebookUser facebookUserResult = (FacebookUser) query.getSingleResult();

		if (facebookUserResult != null) {
			result++;
			
			if(null == facebookUserResult.getAddName()) {
				//Session session = sessionFactory.openSession();
				Query query1 = session.createQuery("select f.addName from com.samar.project.facebookweb.entity.FacebookUser f where f.email =:email");
				query1.setParameter("email", "admin@gmail.com");
				String result1= query1.getSingleResult().toString();
				
				EntityTransaction entityTransaction = session.getTransaction();
				entityTransaction.begin();
				Query query2 = session.createQuery(
						"update com.samar.project.facebookweb.entity.FacebookUser f set f.addName=:image where f.email=:email");
				query2.setParameter("image", result1);
				query2.setParameter("email", facebookUser.getEmail());
				int result2=query2.executeUpdate();
				entityTransaction.commit();
			}
		}
		
		

		return result;
	}

	@Override
	public List<TimeLine> timeLineDAO(FacebookUser facebookUser) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from com.samar.project.facebookweb.entity.TimeLine f where f.email=:email");
		query.setParameter("email", facebookUser.getEmail());
		List<TimeLine> timeLines = query.getResultList();
		return timeLines;
	}

	@Override
	public FacebookUser viewProfileDAO(String email, String password) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(
				"from com.samar.project.facebookweb.entity.FacebookUser f where f.email=:email and f.password=:password");
		query.setParameter("email", email);
		query.setParameter("password", password);
		FacebookUser facebookUserResult = (FacebookUser) query.getSingleResult();
		return facebookUserResult;
	}

	@Override
	public int editProfileDAO(FacebookUser facebookUser) {
		Session session = sessionFactory.openSession();
		EntityTransaction entityTransaction = session.getTransaction();
		entityTransaction.begin();
		Query query = session.createQuery(
				"update com.samar.project.facebookweb.entity.FacebookUser f set f.name=:name, f.password=:password, f.gender=:gender, f.age=:age, f.address=:address where f.email=:email");
		query.setParameter("name", facebookUser.getName());
		query.setParameter("password", facebookUser.getPassword());
		query.setParameter("gender", facebookUser.getGender());
		query.setParameter("age", facebookUser.getAge());
		query.setParameter("address", facebookUser.getAddress());
		query.setParameter("email", facebookUser.getEmail());
		int result=query.executeUpdate();
		entityTransaction.commit();
		return result;
	}

	@Override
	public int deleteProfileDAO(String email) {
		Session session = sessionFactory.openSession();
		EntityTransaction entityTransaction = session.getTransaction();
		entityTransaction.begin();
		
		Query query1 = session.createQuery(
				"delete from com.samar.project.facebookweb.entity.TimeLine f  where f.email=:email");
		query1.setParameter("email", email);
		int result1=query1.executeUpdate();
		
		Query query = session.createQuery(
				"delete from com.samar.project.facebookweb.entity.FacebookUser f  where f.email=:email");
		query.setParameter("email", email);
		int result=query.executeUpdate();
		
		
		
		entityTransaction.commit();
		return result;
	}

	@Override
	public List<FacebookUser> searchProfileDAO(String searchValue) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from com.samar.project.facebookweb.entity.FacebookUser f where f.name like :name");
		query.setParameter("name", "%"+searchValue+"%");
		//query.setParameter("name", searchValue);
		List<FacebookUser> names = query.getResultList();
		return names;
	}

	@Override
	public String loginProfileStatusDAO(String email) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("select f.status from com.samar.project.facebookweb.entity.FacebookUser f where f.email =:email");
		query.setParameter("email", email);
		return query.getSingleResult().toString();
	}

	@Override
	public int uploadPhotoDAO(String retrival_name_of_image, String email) {
		Session session = sessionFactory.openSession();
		EntityTransaction entityTransaction = session.getTransaction();
		entityTransaction.begin();
		Query query = session.createQuery(
				"update com.samar.project.facebookweb.entity.FacebookUser f set f.imageName=:image where f.email=:email");
		query.setParameter("image", retrival_name_of_image);
		query.setParameter("email", email);
		int result=query.executeUpdate();
		entityTransaction.commit();
		return result;
	}

	@Override
	public String getProfilePhotoDAO(String email) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("select concat(f.imageName,',',f.name,',',f.addName) as nameimage from com.samar.project.facebookweb.entity.FacebookUser f where f.email =:email");
		query.setParameter("email", email);
		String result= query.getSingleResult().toString();
		Query query1 = session.createQuery("select count(*) from com.samar.project.facebookweb.entity.FacebookUser f where f.email =:email");
		query1.setParameter("email", email);
		String result1= query.getSingleResult().toString();
		
		return result+","+result1;
	}

	@Override
	public List<FacebookUser> viewAllProfileDAO() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(
				"from com.samar.project.facebookweb.entity.FacebookUser f ");
		List<FacebookUser> facebookUserList =  query.getResultList();
		return facebookUserList;
	}

	@Override
	public int blockUserDAO(String email) {
		Session session = sessionFactory.openSession();
		EntityTransaction entityTransaction = session.getTransaction();
		entityTransaction.begin();
		Query query = session.createQuery(
				"update com.samar.project.facebookweb.entity.FacebookUser f set f.status=:status where f.email=:email");
		query.setParameter("status", "BLOCKED");
		query.setParameter("email", email);
		int result=query.executeUpdate();
		entityTransaction.commit();
		return result;
	}

	@Override
	public int unBlockUserDAO(String email) {
		Session session = sessionFactory.openSession();
		EntityTransaction entityTransaction = session.getTransaction();
		entityTransaction.begin();
		Query query = session.createQuery(
				"update com.samar.project.facebookweb.entity.FacebookUser f set f.status=:status where f.email=:email");
		query.setParameter("status", "ACTIVE");
		query.setParameter("email", email);
		int result=query.executeUpdate();
		entityTransaction.commit();
		return result;
	}

	@Override
	public int createPostService(String retrival_name_of_image, String email, String postMessage,
			FacebookUser facebookUserDetail) {
		int result = 0;
		TimeLine timeline = new TimeLine();
		timeline.setMessage(postMessage);
		timeline.setEmail(email);
		timeline.setImageLocation(retrival_name_of_image);
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");  
	    LocalDateTime now = LocalDateTime.now();  
	    timeline.setRtime(dtf.format(now));
		Session session = sessionFactory.openSession();
		EntityTransaction entityTransaction = session.getTransaction();
		entityTransaction.begin();
		session.save(timeline);
		entityTransaction.commit();
		result++;
		return result;
	}

	@Override
	public int deleteProfileTimeLine(String tid) {
		Session session = sessionFactory.openSession();
		EntityTransaction entityTransaction = session.getTransaction();
		entityTransaction.begin();
		Query query = session.createQuery(
				"delete com.samar.project.facebookweb.entity.TimeLine f  where f.timelineid=:timelineid");
		query.setParameter("timelineid", Long.parseLong(tid));
		int result=query.executeUpdate();
		entityTransaction.commit();
		return result;
	}

	@Override
	public int uploadAddDAO(String retrival_name_of_image, String email) {
		Session session = sessionFactory.openSession();
		EntityTransaction entityTransaction = session.getTransaction();
		entityTransaction.begin();
		Query query = session.createQuery(
				"update com.samar.project.facebookweb.entity.FacebookUser f set f.addName=:addName");
		query.setParameter("addName", retrival_name_of_image);
		int result=query.executeUpdate();
		entityTransaction.commit();
		return result;
	}

}
