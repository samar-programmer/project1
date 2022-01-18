<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/Profile.css">
<link rel="stylesheet"
	href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
	integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p"
	crossorigin="anonymous" />
	<script
  src="https://code.jquery.com/jquery-1.12.4.min.js"
  integrity="sha256-ZosEbRLbNQzLpnKIkEdrPv7lOy9C27hHQ+Xp8a4MxAQ="
  crossorigin="anonymous"></script>
<title>My profile</title>
<script>

function logoutFunction(){
	location.href="UserServlet?method=logout";
}

function searchfunction(){
	debugger;
	location.href="UserServlet?method=searchSetValue&searchValue="+document.getElementById('searchValue').value;
}

/* function getFilePath(){
	var x = document.getElementById("avatar").value;
	console.log("<img src='"+x+"'>");
	  //document.getElementById("profile_image").innerHTML = "<img src='"+x+"'>";
} */

 function initializeProfileDetails(){
	fetch('UserServlet?method=getProfilePhoto')
	  .then(response => response.text())
	  .then(data => {
		  console.log("<img src='"+data+"' alt='profilepicture' >");
		  document.getElementById('profile_image').innerHTML="<img src='"+data.split(",")[0]+"' alt='profilepicture' >";
		  /* document.getElementById('top_photo').innerHTML="<img src='"+data.split(",")[0]+"' alt='profilepicture' >"; */
		  /* for(var i=0; i<data.split(",")[3]; i++){
			  document.getElementById('top_photo'+i).innerHTML="<img src='"+data.split(",")[0]+"' alt='profilepicture' >";
		  } */
		  
		  /* document.getElementById('top_photo2').innerHTML="<img src='"+data.split(",")[0]+"' alt='profilepicture' >"; */
		  document.getElementById('name').innerHTML="User : "+data.split(",")[1];
		  document.getElementById('add').innerHTML="<img src='"+data.split(",")[2]+"' alt='profilepicture' >";
	  });

}  
 
 function deleteTimeLine(timelineId){
	 location.href="UserServlet?method=deleteTimeLine&timelineId="+timelineId;
 }


</script >
<style>

 .add-post-links {
  display: flex;
  margin-top: 10px;
  justify-content: space-between;
}

.add-post-links input[type="submit"] {
  padding: 5px;
} 

 .add-post-links img {
  width: 30px;
  margin-right: 10px;
}
 
</style>
</head>
<body onload="initializeProfileDetails()">
	<nav>
		<div class="container">
			<h2 class="log">facebook</h2>
			<div class="search-bar">
				<!-- <i class="fas fa-search"></i>  --><input type="search" id= "searchValue" placeholder="Search for profile">
				<i onclick="searchfunction()" class="fa fa-search"></i>
			</div>
			<div class="create">
				<!-- <label class="btn btn-primary" for="create-post">Create</label> -->
				<form action="UserServlet?method=logout" method="post">
					<!-- <button class="btn btn-primary" onclick="logoutFunction()">Log out</button> -->
					<input class="btn btn-primary" type="submit" value="Log out">
				</form>
				
				<div class="profile-photo">
					<img src="asset/facebook.png" alt="profile picture">
				</div>
			</div>


		</div>

	</nav>
	
	<!-- ---------------------------------Main section----------------------------------------->
	<main>
		<div class="container">
			<div class="left">
				<a class="profile">
					<div class="" id="profile_image">
							
					</div>
					<div class="handle">
						<h4 id="name" style="margin-top:10px"></h4>
						<p class="text-muted">
							<!-- <input type="file" id="avatar" onchange="getFilePath()" name="avatar"> -->
							<form action="UserServlet?method=uploadPhoto" method="post" enctype="multipart/form-data" name="form1">
            					<input type="file" name="image" id="image" style="margin-top:15px" class="form-control-file">
            					<input type="submit" class="btn btn-primary" style="padding:8px;" name="button" value="upload">
        					</form>
						</p>
					</div>
				</a>
				
				<!-- --------------------side bar--------------------- -->
				
				<div class="sidebar"> 
					<a class="menu-item active">
						<span><i class="fas fa-home"></i></span><h3>TimeLine</h3>
					</a>
					<a class="menu-item" href="ViewProfile.html">
						<span><i class="fas fa-eye"></i></span><h3>View Profile</h3>
					</a>
					<a class="menu-item" href="EditProfile.html">
						<span><i class="fas fa-pen-square"></i></span><h3>Edit Profile</h3>
					</a>
					<!-- <a class="menu-item" href="ViewAllProfile.html">
						<span><i class="fas fa-eye"></i></i></span><h3>view all Profile</h3>
					</a> -->
					<a class="menu-item" href="DeleteProfile.html">
						<span><i class="far fa-trash-alt"></i></span><h3>Delete Profile</h3>
					</a>
						
					
				</div>
				<!-- <button class="btn btn-primary">Create</button> -->
			</div>
			<div class="middle">
				<form class="create-post" method="post" action="UserServlet?method=createPost" enctype="multipart/form-data">
					<!-- <div class="profile-photo" id="top_photo">
					</div> -->
					<div class="profile-photo">
							<label for="postPic"> <img src="asset/upload.png" alt="" />
							</label> <input type="file" name="postPics" accept="image/*" id="postPic" />
							<input type="submit" value="post" />
					</div>
					
					<textarea name="postMessage" id="postMessage" rows="3"
						placeholder="what's in  your mind?" style="margin-top:10px"></textarea>
					<!-- <input type="text" placeholder="what is in your mind?" id="create-post"> -->
					<input type="submit" class="btn btn-primary" value="post">
					
				</form>
				
				<!-- --------------------------------timeline----------------------------- -->
				
				  <%@page import="com.samar.project.facebookweb.entity.TimeLine" %>
				  <%@page import="java.util.List" %>
				  <%@page import="java.util.Iterator"%> 
					<%
						List<TimeLine> timeLines=(List<TimeLine>)request.getAttribute("timelineData");
						System.out.println(timeLines);
						System.out.println("sasi");
					
					%>
					
					<%
					Iterator<TimeLine> iterator = timeLines.iterator();
					
					while(iterator.hasNext())  
					{
						TimeLine timeLine = iterator.next();
					%>
					
					<div class="feeds">
					<div class="feed">
						<div class="head">
							<div class="user">
								<div class="profile-photo" id="top_photo">
								</div>
								<div class="ingo">
									<h3><%=timeLine.getMessage()%></h3>
									<small><%=timeLine.getRtime()%></small>
								</div>
							</div>
							<span class="edit" onclick="deleteTimeLine(<%=timeLine.getTimelineid()%>)">
									<i class="fas fa-trash-alt"></i>
								</span>
						</div>
						
						
						<div class="photo">
							<img src="<%=timeLine.getImageLocation()%>" alt="pic"/>
						</div>
						
						<div class="action-buttons">
							<div class="interaction-buttons">
								<span><i class="fas fa-heart"></i></span>
								<span><i class="fas fa-thumbs-down"></i></span>
								<span><i class="fas fa-reply"></i></span>
							</div>
							<div class="bookmark">
							<span><i class="far fa-comment"></i></span>
							</div>
						</div>
						
					</div>
				</div>
					
					
					<% }%>
					
					<%timeLines=null; %>
					
					<%-- <%timeLines=null; %> --%>
				
				<!-- <div class="feeds">
					<div class="feed">
						<div class="head">
							<div class="user">
								<div class="profile-photo" id="top_photo1">
								</div>
								<div class="ingo">
									<h3>sasikumar</h3>
									<small>India, 15 minute ago</small>
								</div>
							</div>
							<span class="edit">
									<i class="fas fa-ellipsis-h"></i>
								</span>
						</div>
						
						
						<div class="photo" id="top_photo2">
						</div>
						
						<div class="action-buttons">
							<div class="interaction-buttons">
								<span><i class="fas fa-heart"></i></span>
								<span><i class="fas fa-thumbs-down"></i></span>
								<span><i class="fas fa-reply"></i></span>
							</div>
							<div class="bookmark">
							<span><i class="far fa-comment"></i></span>
							</div>
						</div>
						
					</div>
				</div> -->
				
			</div>
			<div class="right">
				<div class="message">
					<div class="heading">
						<h3 style="margin:0 auto">Advertisement</h3>
					</div>
					<!-- <img src="asset/facebook.png"> -->
					<div id="add"></div>
				</div>
			</div>
		</div>
	</main>

</body>
</html>