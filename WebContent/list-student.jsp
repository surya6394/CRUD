<%@ page import="java.util.*, com.ravi.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List of student</title>
<link rel="stylesheet" href="css/style.css">
</head>
	
	<%
					// get the students for the request object (sent by the server)
		
			List<Student> theStudents = (List<Student>) request.getAttribute("STUDENT_LIST");

					 %>

<body>

					<div id="wrapper">
						<div id="header">
								<h2>Buddha Insitute Of Technology (BIT)</h2>
						</div>
					</div>
					
					<div id="container">
						<div id="content">
						
							<table align="center">	
							
								<tr>
									<th>Id</th>
									<th>First Name</th>
									<th>Last Name</th>
									<th>Email</th>
								</tr>
								
								<% for( Student tempStudent : theStudents){ %>
								
								<tr>
										<td><%=tempStudent.getId() %></td>								
										<td><%=tempStudent.getFirstName() %></td>
										<td><%=tempStudent.getLastName() %></td>
										<td><%=tempStudent.getEmail() %></td>
								</tr>
								
								<%} %>
								
								</table>
						</div>
					</div>
					
					
</body>
</html>