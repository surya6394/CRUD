package com.ravi;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private StudentDbUtil studentDbUtil;
	
	@Resource(name="jdbc/student_db_tracker")
	private DataSource dataSource;
	
	@Override
	public void init()throws ServletException{
		super.init();
		// Create our studentDbUtil..... ans pass in the conn pool / datasource
		try {
			studentDbUtil = new StudentDbUtil(dataSource);
		}catch(Exception exc) {
			throw new ServletException(exc);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			
			// if the command is missing, then default is listing all students
			if(theCommand == null) {
				theCommand = "LIST";
			}
			
			//route to appropriate method
			switch(theCommand) {
				case "LIST":
					listStudents(request,response);
					 break;
				case "ADD":
					 addStudent(request,response);
					 break;
				case "LOAD":
					 loadStudent(request,response);
					 break;
				case "UPDATE":
					updateStudent(request,response);
					 break;
				case "DELETE":
					 deleteStudent(request,response);
					 break;
				default:
					 listStudents(request,response);
					 break;
			}
		}catch(Exception exc) {
			throw new ServletException(exc);
		}
		
	}
	
	// List of students
	
	private <student, children> void listStudents(HttpServletRequest request,HttpServletResponse response)throws Exception{
		// get students from dbUtil
		List<children> students = studentDbUtil.getStudents();
		
		// add students to the request
		request.setAttribute("STUDENT_LIST", students);
		
		// send to JSP Page(View)
		RequestDispatcher dispatcher = request.getRequestDispatcher("./jstl-list-view.jsp");
		
		dispatcher.forward(request, response);
	}
	
	// Add student
	
	private void addStudent(HttpServletRequest request, HttpServletResponse response)throws Exception{
		// reading student info from data
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		// create a new Student object
		
		Student theStudent = new Student(firstName,lastName,email);
		
		//passing theStudent to StudentDbUtil
		studentDbUtil.addStudent(theStudent);
		
		// send back to mainpage
		listStudents(request,response);
	}
	
	// Load Student
	
	private void loadStudent(HttpServletRequest request,HttpServletResponse response)throws Exception{
		//read student id from form data
		String theStudentId = request.getParameter("studentId");
		
		// get student from dbutil
		Student theStudent = studentDbUtil.getStudent(theStudentId);
		
		// place student in request attribute
		request.setAttribute("THE_STUDENT", theStudent);
		
		// send to jsp page : update-student-form.jsp
		RequestDispatcher dispatcher = request.getRequestDispatcher("./student-updation-form.jsp");
		
		dispatcher.forward(request, response);
	}
	
	// Delete Student
	private void deleteStudent(HttpServletRequest request, HttpServletResponse response)throws Exception{
		// reading studentId from form data
		String theStudentId = request.getParameter("studentId");
		
		// delete student from database
		studentDbUtil.deleteStudent(theStudentId);
		
		// send them block to "list students" page
		listStudents(request,response);
	}
	
	
	
	// Update Student
	
	  private void updateStudent(HttpServletRequest request, HttpServletResponse response)throws Exception{
		  // reading student info from form data
		  
		  int id = Integer.parseInt(request.getParameter("studentId"));
		  String firstName = request.getParameter("firstName");
		  String lastName = request.getParameter("lastName");
		  String email = request.getParameter("email");
		  
		  // create a new Student object
		  
		  Student theStudent = new Student(id,firstName,lastName,email);
		  
		  // Passing theStudent to StudentDbUtil
		  studentDbUtil.updateStudent(theStudent);
		  
		  // send back to mainpage
		  listStudents(request, response);
	  }

}

	









