Complaint Manager System A web-based application designed to streamline complaint submission and resolution for employees and admins.

Contents Project Overview

	01.Features
	02.Technologies Used
	03.System Architecture
	04.Database Schema
	05.Setup & Installation


Project Overview 

The Complaint Manager solves inefficiencies by,

	Allowing employees to submit complaints.
	Enabling admins to track, manage, and respond to complaints.

Built using JSP (Frontend), Java Servlets (Backend), and MySQL (Database).

Features 

	Employee Features 
		Submit new complaints. 
		View their complaint history. 
		Check admin responses.

	Admin Features 
		View all complaints. 
		Respond to complaints. 
	 	Update complaint status.

Technologies 

	Technology Frontend	JSP, CSS, JavaScript 
	Backend	Java Servlets 
	Database	MySQL 
	Architecture	MVC (Model-View-Controller) 

System Architecture (MVC)

	Model -  DAO 
	View JSP Pages / CSS/JS 
	Controller Servlets → Handle HTTP requests 


Setup & Installation Prerequisites 

	JDK 8+
	Apache Tomcat 9+
	MySQL 5.7+
	
	Steps Clone the repository
	
	bash git clone https://github.com/DTY17/cmplainManager
	Import into IntelliJ as a Maven project.
	Configure MySQL
	
	Create a database complaint_db.
	Deploy on Tomcat
	Run the project using Tomcat server.


