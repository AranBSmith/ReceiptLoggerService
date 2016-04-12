package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.sql.DataSource;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import model.CredentialSubmissionResponse;
import model.LoginResponse;

public class UserDAO extends DAO {

	private DataSource dataSource;

	public UserDAO(){
		this.dataSource = super.getMySQLDataSource();
	}
	
	public LoginResponse login(String email, String password) {
		
		LoginResponse loginResponse = new LoginResponse();
		String sql = "select password from Users where email = ?";
		
		Connection conn = null;		
		try{
			conn = dataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet resultSet = ps.executeQuery();
			
			if(resultSet.next()){
				// there is a result, check if the password is the same.
				String testPassword = resultSet.getString("password");
				if (password.equals(testPassword)){
					loginResponse.setSuccess();
				}
			}
			
			return loginResponse;
		} catch(SQLException e){
			loginResponse.setResponse(e.getMessage() + " " + e.getSQLState());
			return loginResponse;
		} catch (NullPointerException e){
			loginResponse.setResponse("There was a null pointer exception when trying to interact with the DB"
					+ "" + e.getMessage() + " " + e.toString());
			return loginResponse;
		} 
		finally{
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}
	}
	
	public CredentialSubmissionResponse insertUserCredentials(HashMap<String,String> userCredentials) {
		CredentialSubmissionResponse credSubResponse = new CredentialSubmissionResponse();
		
		String sql = "INSERT INTO Users (email, password) VALUES (?, ?)";
		
		Connection conn = null;
		int count = 0;
	
		try {
			conn = dataSource.getConnection();
			
			for(String email : userCredentials.keySet()){
				String password = userCredentials.get(email);
				
				// insert this credential pair into the database
				PreparedStatement ps = null;
				ps = conn.prepareStatement(sql);
				ps.setString(1, email);
				ps.setString(2, password);
								
				try{
					count = ps.executeUpdate();
				} catch (MySQLIntegrityConstraintViolationException e){
					credSubResponse.appendMessage(e.getMessage());
				}
				ps.close();
			}
		} catch (SQLException e) {
			credSubResponse.appendMessage(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					credSubResponse.appendMessage(e.getMessage());
				}
				if(count > 0){
					credSubResponse.setSuccess();
				}
			} 
		}
		return credSubResponse;
	}
}
	


