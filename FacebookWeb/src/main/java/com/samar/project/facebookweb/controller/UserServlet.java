package com.samar.project.facebookweb.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
//json
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

//mail
/*import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.mail.Session;
import javax.mail.Transport;*/

import com.samar.project.facebookweb.entity.FacebookUser;
import com.samar.project.facebookweb.entity.TimeLine;
import com.samar.project.facebookweb.interfaces.FacebookServiceInterface;
import com.samar.project.facebookweb.utility.ServiceFactory;



public class UserServlet extends HttpServlet {
	int logincount = 0;
	HttpSession httpSession ;
	FacebookUser facebookUserDetail;
	@SuppressWarnings("deprecation")
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method=request.getParameter("method");
		
		
		
		FacebookServiceInterface facebookServiceInterface=ServiceFactory.createObject();
		
		if(method.equals("register")) {
			
			String name=request.getParameter("firstName") +"."+ request.getParameter("lastName");
			String password=request.getParameter("password");
			String email=request.getParameter("email");
			String gender=request.getParameter("gender");
			String age=request.getParameter("age");
			String address=request.getParameter("address");
			FacebookUser facebookUser =new FacebookUser();
			facebookUser.setName(name);
			facebookUser.setPassword(password);
			facebookUser.setEmail(email);
			facebookUser.setGender(gender);
			facebookUser.setAge(age);
			facebookUser.setAddress(address);
			facebookUser.setStatus("ACTIVE");
			int result=facebookServiceInterface.createProfileService(facebookUser);
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			
			
			
			if(result>0) {
				ServletContext c = this.getServletContext();
				String path = c.getRealPath("/");
				File f2 = new File(path + "/document/" + email + "/profilePhoto/");
				f2.mkdirs();
				out.println("<html><body><center>");
				out.println("<br><br><br><br><font size=5 color=blue><b>Profile created successfully</b></font>");
				out.println("<br>Your Username is "+email+"  and password is "+password);
				out.println("<br><a href=login.html>Continue...</a>");
				out.println("</center></body></html>");
			}
			else {
				out.println("<html><body><center>");
				out.println("could not create profile");
				out.println("</center></body></html>");
				RequestDispatcher requestDispatcher=getServletContext().getRequestDispatcher("/register.html");
				requestDispatcher.include(request, response);
			}
			
		}
		if(method.equals("login")) {
			
			if("admin@gmail.com".equals(request.getParameter("email")) && "admin".equals(request.getParameter("password"))) {
				RequestDispatcher requestDispatcher=getServletContext().getRequestDispatcher("/admin.html");
				requestDispatcher.include(request, response);
				httpSession=request.getSession(true);
			}else {
				try {
					String password=null;
					String email=null;
					System.out.println(logincount);
					httpSession=request.getSession(true);
					
					System.out.println(httpSession.isNew());
					
					if(!httpSession.isNew()) {
						password=httpSession.getAttribute("password").toString();
						 email=httpSession.getAttribute("email").toString();
					}else {
						 password=request.getParameter("password");
						 email=request.getParameter("email");
					}
					
					FacebookUser facebookUser=new FacebookUser();
					facebookUser.setPassword(password);
					facebookUser.setEmail(email);
					
					String status=facebookServiceInterface.loginProfileStatusService(email);
					
					if(status.equals("ACTIVE")) {

						int result=facebookServiceInterface.loginProfileService(facebookUser);
						
						if(result>0) {
							if(logincount == 0) {
								httpSession.setAttribute("loginCount", "login");
								logincount++;
							}
							
							httpSession.setAttribute("email", email);
							httpSession.setAttribute("password", password);
							httpSession.setAttribute("loginCount", "loginAlready");
							facebookUserDetail = new FacebookUser();
							facebookUserDetail.setEmail(email);
							//sendMail(email);
							List<TimeLine> timeLines = facebookServiceInterface.timeLineService(facebookUserDetail);
							request.setAttribute("timelineData", timeLines);
							RequestDispatcher rd=getServletContext().getRequestDispatcher("/profile.jsp");
							rd.forward(request, response);
						}
						else {
							httpSession.invalidate();
							response.setContentType("text/html");
							PrintWriter out=response.getWriter();
							out.println("<html><body><center>");
							out.println("Invalid id and password");
							out.println("</center></body></html>");
							RequestDispatcher requestDispatcher=getServletContext().getRequestDispatcher("/login.html");
							requestDispatcher.include(request, response);
						}
					}else {
						httpSession.invalidate();
						response.setContentType("text/html");
						PrintWriter out=response.getWriter();
						out.println("<html><body><center>");
						out.println("<h3 style='color:blue'>Youd id is blocked please contact admin</h3>");
						out.println("<br><a href=index.html>ClickHere to Home Page...</a>");
						out.println("</center></body></html>");
						
					}
					
					
				}catch(Exception e) {
					httpSession.invalidate();
					response.setContentType("text/html");
					PrintWriter out=response.getWriter();
					out.println("<html><body><center>");
					out.println("Invalid id and password");
					out.println("<br><a href=login.html>Continue to login...</a>");
					out.println("</center></body></html>");
					e.printStackTrace();
				}
			}
			
			
		}
		
		
		
		/*
		 * if (method.equals("UserHomePage")) { HttpSession hs =
		 * request.getSession(true); String email =
		 * hs.getAttribute("userid").toString(); out.println("<table border=0>");
		 * out.println("<tr><td>Welcome " + email +
		 * " </td><td></td><td></td><td></td><td></td><td><a href=UserServlet?method=viewProfile><button>View Profile</button></a></td><td><a href=UserServlet?method=editProfile><button>Edit Profile</button></a></td><td><a href=UserServlet?method=searchProfile><button>Search Profile</button></a></td><td><a href=UserServlet?method=uploadphoto><button>upload photo</button></a></td><td><a href=UserServlet?method=deleteprofile><button>Delete Profile</button></a></td><td><a href=UserServlet?method=friendrequest><button>Friend Request</button></a></td></tr>"
		 * ); out.println("</table>"); FacebookUser facebookUser = new FacebookUser();
		 * 
		 * facebookUser.setEmail(email); List<TimeLine> timeLines =
		 * facebookServiceInterface.timeLineService(facebookUser); if (timeLines.size()
		 * > 0) { out.println("<table border=0></table>");
		 * out.println("<table border=0>"); for (TimeLine timeLine : timeLines) { if
		 * (timeLine.getReceiver_Id().equals(email)) { out.println("<tr><td>" +
		 * timeLine.getSender_id() + "</td><td><textarea cols=100 rows=5>" +
		 * timeLine.getMessage() + "</textarea></td></tr>"); out.println(
		 * "<tr><td></td><td><a href=UserServlet?method=like>Like (" +
		 * timeLine.getMessagelike() +
		 * ")</a><a href=UserServlet?method=dislike>Dislike(" + timeLine.getDislike() +
		 * ")</a></td><td><a href=UserServlet?method=reply>reply</td></tr>"); } } } else
		 * {
		 * 
		 * out.println("<tr><td></td><td> no timeline message</td></tr>"); }
		 * out.println("</table>"); }
		 */
		 
		
		if(method.equals("reply")) {
			
		}
		
		if(method.equals("viewProfile")) {
			 facebookUserDetail=facebookServiceInterface.viewProfileService(httpSession.getAttribute("email").toString(), httpSession.getAttribute("password").toString());
			 JSONObject jsonObject = new JSONObject(facebookUserDetail);
			 response.setContentType("application/json");
			 PrintWriter outView=response.getWriter(); 
			 outView.print(jsonObject);
		}
		
		if(method.equals("editProfile")) {
			FacebookUser facebookUser=new FacebookUser();
			facebookUser.setName(request.getParameter("firstName")+"."+request.getParameter("lastName"));
			facebookUser.setEmail(request.getParameter("originalEmail"));
			facebookUser.setPassword(request.getParameter("password"));
			facebookUser.setGender(request.getParameter("gender"));
			facebookUser.setAge(request.getParameter("age"));
			facebookUser.setAddress(request.getParameter("address"));
			int result = facebookServiceInterface.editProfileService(facebookUser);
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			if(result>0) {
				httpSession.setAttribute("password", facebookUser.getPassword());
				out.println("<html><body><center>");
				out.println("<br><br><br><br><font size=5 color=blue><b>Profile updated successfully</b></font>");
				out.println("<br>Your Username is "+facebookUser.getEmail());
				out.println("<br><a href=UserServlet?method=login>Continue to timeline...</a>");
				out.println("</center></body></html>");
			}
			else {
				out.println("<html><body><center>");
				out.println("<p style='color:white'>could not update profile</p>");
				out.println("<br><a href=UserServlet?method=login>Continue to timeline...</a>");
				out.println("</center></body></html>");
				
			}
		}
		
		
		if((method.trim()).equals("searchSetValue")) {
			httpSession.setAttribute("searchValue", request.getParameter("searchValue"));
			RequestDispatcher requestDispatcher=getServletContext().getRequestDispatcher("/searchProfile.html");
			requestDispatcher.forward(request, response);
		}
		
		if(method.contentEquals("searchProfiles")) {
			List<FacebookUser> names=facebookServiceInterface.searchProfileService(httpSession.getAttribute("searchValue").toString());
			 response.setContentType("application/json");
			 PrintWriter outView=response.getWriter(); 
			 Gson gson = new GsonBuilder().setPrettyPrinting().create();
			 String json = gson.toJson(names);
			 outView.print(json);
		}
		
		
		if(method.equals("uploadPhoto")) {
			
			ServletContext s = getServletContext();
			String path = s.getRealPath("/document/" + httpSession.getAttribute("email").toString() + "/profilePhoto/");
			
			
			try {
				@SuppressWarnings("deprecation")
				DiskFileUpload d = new DiskFileUpload();
				@SuppressWarnings({ "deprecation", "rawtypes" })
				List x= d.parseRequest(request);
				FileItem f;
				String image = "";
					f = (FileItem) x.get(0);
					String n = f.getName();
						File f1 = new File(n);
						image = System.currentTimeMillis() + f1.getName();
						File w = new File(path, image);
						f.write(w);
						String retrival_name_of_image = "document/"+httpSession.getAttribute("email").toString()+"/profilePhoto/"+image;
						int result = facebookServiceInterface.uploadPhotoService(retrival_name_of_image, httpSession.getAttribute("email").toString());
						response.setContentType("text/html");
						PrintWriter out=response.getWriter();
						if(result>0) {
							out.println("<html><body><center>");
							out.println("<br><br><br><br><font size=5 color=blue><b>Profile Photo Uploaded successfully</b></font>");
							out.println("<br><a href=UserServlet?method=login>Continue to timeline...</a>");
							out.println("</center></body></html>");
						}else {
							out.println("<html><body><center>");
							out.println("<p style='color:white'>could not upload photo</p>");
							out.println("<br><a href=UserServlet?method=login>Continue to timeline...</a>");
							out.println("</center></body></html>");
						}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		if(method.equals("getProfilePhoto")) {
			
			String image_relative_path = facebookServiceInterface.getProfilePhotoService(httpSession.getAttribute("email").toString());
			if(image_relative_path != null) {
				response.setContentType("text/html");
				PrintWriter out=response.getWriter();
				out.print(image_relative_path);
			}
			
			
		}
		
		
		if(method.equals("getAdminData")) {
			List<FacebookUser> facebookUser=facebookServiceInterface.viewAllProfileService();
			 response.setContentType("application/json");
			 PrintWriter outView=response.getWriter(); 
			 Gson gson = new GsonBuilder().setPrettyPrinting().create();
			 String json = "{ \"data\": "+gson.toJson(facebookUser)+"}";
			 outView.print(json);
		}
		
		if(method.equals("createPost")) {
			
			ServletContext s = getServletContext();
			String path = s.getRealPath("/document/" + httpSession.getAttribute("email").toString() + "/profilePhoto/");
			
			
			try {
				@SuppressWarnings("deprecation")
				DiskFileUpload d = new DiskFileUpload();
				@SuppressWarnings({ "deprecation", "rawtypes" })
				List x= d.parseRequest(request);
				FileItem f;
				Iterator t1 = x.iterator();
				String postMessage = "";
				String image = "";
					f = (FileItem) x.get(0);
					String n = f.getName();
						File f1 = new File(n);
						image = System.currentTimeMillis() + f1.getName();
						File w = new File(path, image);
						f.write(w);
						
						while (t1.hasNext()) {
							f = (FileItem) t1.next();

							if (f.isFormField()) {
								String a = f.getFieldName();
								String b = f.getString();

								if (a.equalsIgnoreCase("postMessage")) {
									postMessage = b;

								}
							}
						}
						
						String retrival_name_of_image = "document/"+httpSession.getAttribute("email").toString()+"/profilePhoto/"+image;
						int result = facebookServiceInterface.createPostService(retrival_name_of_image, httpSession.getAttribute("email").toString(),postMessage, facebookUserDetail );
						response.setContentType("text/html");
						PrintWriter out=response.getWriter();
						if(result>0) {
							List<TimeLine> timeLines = facebookServiceInterface.timeLineService(facebookUserDetail);
							request.setAttribute("timelineData", timeLines);
							RequestDispatcher rd=getServletContext().getRequestDispatcher("/profile.jsp");
							rd.forward(request, response);
						}else {
							out.println("<html><body><center>");
							out.println("<p style='color:white'>could not create post</p>");
							out.println("<br><a href=UserServlet?method=login>Continue to timeline...</a>");
							out.println("</center></body></html>");
						}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			
		if(method.equals("createAdvertisement")) {
			ServletContext s = getServletContext();
			String path = s.getRealPath("/document/" + "admin@gmail.com" + "/profilePhoto/");
			
			
			try {
				@SuppressWarnings("deprecation")
				DiskFileUpload d = new DiskFileUpload();
				@SuppressWarnings({ "deprecation", "rawtypes" })
				List x= d.parseRequest(request);
				FileItem f;
				String image = "";
					f = (FileItem) x.get(0);
					String n = f.getName();
						File f1 = new File(n);
						image = System.currentTimeMillis() + f1.getName();
						File w = new File(path, image);
						f.write(w);
						String retrival_name_of_image = "document/"+"admin@gmail.com"+"/profilePhoto/"+image;
						int result = facebookServiceInterface.uploadAddService(retrival_name_of_image, "admin@gmail.com");
						response.setContentType("text/html");
						PrintWriter out=response.getWriter();
						if(result>0) {
							out.println("<html><body><center>");
							out.println("<br><br><br><br><font size=5 color=blue><b>Addvertisement Uploaded successfully</b></font>");
							out.println("<br><a href=admin.html>Continue to admin page...</a>");
							out.println("</center></body></html>");
						}else {
							out.println("<html><body><center>");
							out.println("<p style='color:white'>could not upload photo</p>");
							out.println("<br><a href=admin.html>Continue to admin page...</a>");
							out.println("</center></body></html>");
						}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(method.equals("blockUser")) {
			int result = facebookServiceInterface.blockUserService(request.getParameter("email"));
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			if(result>0) {
				out.println("<html><body><center>");
				out.println("<br><br><br><br><font size=5 color=blue><b>User Blocked successfully</b></font>");
				out.println("<br><a href=admin.html>Continue to admin panel...</a>");
				out.println("</center></body></html>");
			}else {
				out.println("<html><body><center>");
				out.println("<p style='color:white'>could not blocked user</p>");
				out.println("<br><a href=UserServlet?method=login>Continue to admin panel...</a>");
				out.println("</center></body></html>");
			}
		}
		
		if(method.equals("unBlockUser")) {
			int result = facebookServiceInterface.unBlockUserService(request.getParameter("email"));
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			if(result>0) {
				out.println("<html><body><center>");
				out.println("<br><br><br><br><font size=5 color=blue><b>User Un-Blocked successfully</b></font>");
				out.println("<br><a href=admin.html>Continue to admin panel...</a>");
				out.println("</center></body></html>");
			}else {
				out.println("<html><body><center>");
				out.println("<p style='color:white'>could not Un-blocked user</p>");
				out.println("<br><a href=UserServlet?method=login>Continue to admin panel...</a>");
				out.println("</center></body></html>");
			}
		}
		
		if(method.equals("logout")) {
			if(null != httpSession) {
				httpSession.invalidate();
			}
			
			logincount=0;
			RequestDispatcher requestDispatcher=getServletContext().getRequestDispatcher("/index.html");
			requestDispatcher.forward(request, response);
		}
		
		
		if(method.equals("deleteProfile")) {
			String email = request.getParameter("email");
			int result = facebookServiceInterface.deleteProfileService(email);
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			if(result>0) {
				if(null != httpSession) {
					httpSession.invalidate();
				}
				out.println("<html><body><center>");
				out.println("<br><br><br><br><font size=5 color=blue><b>Profile Deleted successfully</b></font>");
				out.println("<br>Your Username is "+email);
				out.println("<br><a href=register.html>Continue to Sign up...</a>");
				out.println("</center></body></html>");
			}
			else {
				out.println("<html><body><center>");
				out.println("<p style='color:white'>could not delete profile</p>");
				out.println("<br><a href=UserServlet?method=login>Continue to timeline...</a>");
				out.println("</center></body></html>");
				
			}
		}
		
		if(method.equals("deleteTimeLine")) {
			String tid = request.getParameter("timelineId");
			int result = facebookServiceInterface.deleteProfileTimeLine(tid);
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			if(result>0) {
				out.println("<html><body><center>");
				out.println("<br><br><br><br><font size=5 color=blue><b>TimeLine Deleted successfully</b></font>");
				out.println("<br>Your TimeLine Id is "+tid);
				out.println("<br><a href=UserServlet?method=login>Continue to Sign up...</a>");
				out.println("</center></body></html>");
			}
			else {
				out.println("<html><body><center>");
				out.println("<p style='color:white'>could not delete TimeLine</p>");
				out.println("<br><a href=UserServlet?method=login>Continue to timeline...</a>");
				out.println("</center></body></html>");
				
			}
		}
		
		
		
	}
	
	
	/*
	 * public static void SendEmail(String email) {
	 * 
	 * // Recipient's email ID needs to be mentioned. String to = email;
	 * 
	 * // Sender's email ID needs to be mentioned String from = "web@gmail.com";
	 * 
	 * // Assuming you are sending email from localhost String host = "localhost";
	 * 
	 * // Get system properties Properties properties = System.getProperties();
	 * 
	 * // Setup mail server properties.setProperty("mail.smtp.host", host);
	 * 
	 * // Get the default Session object. Session session =
	 * Session.getDefaultInstance(properties);
	 * 
	 * try { // Create a default MimeMessage object. MimeMessage message = new
	 * MimeMessage(session);
	 * 
	 * // Set From: header field of the header. message.setFrom(new
	 * InternetAddress(from));
	 * 
	 * // Set To: header field of the header.
	 * message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	 * 
	 * // Set Subject: header field message.setSubject("Login Details");
	 * 
	 * // Now set the actual message message.
	 * setText("You are loggged in , if u not kindly change your password thank u");
	 * 
	 * // Send message Transport.send(message);
	 * System.out.println("Sent message successfully...."); } catch
	 * (MessagingException mex) { mex.printStackTrace(); }
	 * 
	 * 
	 * }
	 */

		     
	
	

}
