package com.ravi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil<children> {

	private DataSource dataSource;

	public StudentDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	                 															// Get List Off All Students
	@SuppressWarnings("unchecked")
	public List <children> getStudents(){
		List <children> students = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			myConn = dataSource.getConnection();
			// Step 3: Create a sql Statement
			String sql = "SELECT  *FROM children";
			myStmt = myConn.createStatement();
			
			// Step 4: Execute SQL query
			myRs = myStmt.executeQuery(sql);
			
			// Converting rows into student student class objects
			while(myRs.next()) {
				// Retriving data from resultset
				int id = myRs.getInt("id");
				String firstName = myRs.getString("first_name");
				String lastName = myRs.getString("last_name");
				String email = myRs.getString("email");
				
				// Create new student object
				
				Student tempChildren = new Student(id,firstName,lastName,email);
				students.add((children) tempChildren);
			}
			
		}catch(Exception exc) {
			exc.printStackTrace();
		}
		finally {
			// clean jdbc resource (myStmt, myRs, myCon);
			close(myConn,myRs,myStmt);
		}
		return students;
	}

	 																				// Get a Single Student By Id
		public Student getStudent(String theStudentId) throws Exception{
			Student theStudent = null;
			
			Connection myConn = null;
			PreparedStatement myStmt = null;
			ResultSet myRs = null;
			
			int StudentId;
			
			try {
				// convert studentId to int
				StudentId = Integer.parseInt(theStudentId);
				
				// get connection
				myConn = dataSource.getConnection();
				
				// create sql to get selected student
				String sql = "select *from children where id=?";
				
				// create prepared statement
				myStmt = myConn.prepareStatement(sql);
				
				// parameter binding
				myStmt.setInt(1, StudentId);
				
				//execute query
				myRs = myStmt.executeQuery();
				
				// retrieve data from result set row
				if(myRs.next()) {
					String firstName = myRs.getString("first_name");
					String lastName = myRs.getString("last_name");
					String email = myRs.getString("email");
					
					// using the theStudentId during object construction
					theStudent = new Student(StudentId, firstName, lastName, email);
				}
				else {
					throw new Exception("Could not find student id: "+StudentId);
				}
				
				return theStudent;
			}finally {
				// cleanup JDBC objects
				close(myConn,myRs,myStmt);
			}
		
		}
	
	
																							// Add A New Student
	
	public void addStudent(Student theStudent)throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			// get connection
			myConn = dataSource.getConnection();
			
			// create sql query
			String sql = "insert into children (first_name,last_name,email) values (?,?,?)";
			
			myStmt = myConn.prepareStatement(sql);
			
			// set param values for the student
			
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			
			// execute query
			
			myStmt.execute();
			
		}finally {
			// cleanup JDBC resource
			close(myConn,null,myStmt);
		}
	}
	
																								// Update A Student
	
	public void updateStudent(Student theStudent)throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		
		try {
			// get connection
			myConn = dataSource.getConnection();
			
			// create sql query
			String sql = "update children set first_name=?, last_name=?, email=? where id=?";
			
			myStmt = myConn.prepareStatement(sql);
			
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			myStmt.setInt(4, theStudent.getId());
			
			// execute query
			
			myStmt.execute();
		}
		finally {
			// cleanup JDBC resource
			close(myConn,null,myStmt);
		}
	}
	
																									// Delete A Student
	
	public void deleteStudent(String theStudentId)throws Exception{
		Connection myConn = null;
		PreparedStatement myStmt = null;
		try {
			// convert studentId to int
			int StudentId = Integer.parseInt(theStudentId);
			
			//get connection
			myConn = dataSource.getConnection();
			
			// create sql to get selected student
			String sql = "delete from children where id = ?";
			
			//create prepared statement
			myStmt = myConn.prepareStatement(sql);
			
			// parameter binding
			myStmt.setInt(1, StudentId);
			
			// execute query
			myStmt.execute();
		}
		finally {
			// cleanup JDBC objects
			close(myConn,null,myStmt);
		}
	}
	
	
																									        // Cleanup Code

	private void close(Connection myConn,ResultSet myRs,Statement myStmt) {
		
		try {
			if(myConn != null) {
				myConn.close();
			}
			
			if(myRs != null) {
				myRs.close();
			}
			
			if(myStmt != null) {
				myStmt.close();
			}
		}catch(Exception exc) {
			exc.printStackTrace();
		}
		
		}


	
}
