package dao;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.sql.DataSource;

import org.apache.tomcat.util.codec.binary.Base64;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import model.CredentialSubmissionResponse;
import model.LoginResponse;

public class UserDAO extends DAO {

	private DataSource dataSource;

	public UserDAO(){
		this.dataSource = super.getMySQLDataSource();
	}
	
	private String getSalt(String email) throws SQLException {
		String sql = "select salt from Users where email = ?";
		Connection conn = null;		
		
		conn = dataSource.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, email);
		ResultSet resultSet = ps.executeQuery();

		String salt = "";
		
		if(resultSet.next()){
			salt = resultSet.getString("salt");
			
			ps.close();
			conn.close();
		}
		
		return salt;
	}
	
	public LoginResponse login(String email, String password) {
		
		LoginResponse loginResponse = new LoginResponse();
		Connection conn = null;
		
		try{
			// first get salt.
			String salt = "";
			salt = getSalt(email);
			String passAndSalt = password.concat(salt);
			
			String sql = "select password from Users where email = ?";
			conn = null;		
			
			conn = dataSource.getConnection();
			PreparedStatement ps = null;
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet resultSet = ps.executeQuery();
			
			if(resultSet.next()){
				// there is a result, check if the password is the same.
				
				String hashedPassword;
				
				try {
					MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
					messageDigest.update(passAndSalt.getBytes("UTF-8"));
					byte[] digest = messageDigest.digest();
					hashedPassword = Base64.encodeBase64String(digest);
					loginResponse.appendMessage("hashed password is: " + hashedPassword);
					
					String testPassword = resultSet.getString("password");
					loginResponse.appendMessage("stored password is: " + testPassword);
					if (hashedPassword.equals(testPassword)){
						loginResponse.setSuccess();
					}
					
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
					loginResponse.appendMessage(e.getMessage());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					loginResponse.appendMessage(e.getMessage());
				}
			}
			
			return loginResponse;
		} catch(SQLException e) {
			loginResponse.setResponse(e.getMessage() + " " + e.getSQLState());
			return loginResponse;
		} catch (NullPointerException e) {
			loginResponse.setResponse("There was a null pointer exception when trying to interact with the DB"
					+ "" + e.getMessage() + " " + e.toString());
			return loginResponse;
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} 
				password = null;
			}
		}
	}
	
	public CredentialSubmissionResponse insertUserCredentials(HashMap<String, String[]> userCredentials) {
		CredentialSubmissionResponse credSubResponse = new CredentialSubmissionResponse();
		
		String sql = "INSERT INTO Users (email, password, salt) VALUES (?, ?, ?)";
		
		Connection conn = null;
		int count = 0;
	
		try {
			conn = dataSource.getConnection();
			
			for(String email : userCredentials.keySet()){
				String[] saltAndHash = userCredentials.get(email);
				
				// insert this credential pair into the database
				PreparedStatement ps = null;
				ps = conn.prepareStatement(sql);
				ps.setString(1, email);
				ps.setString(2, saltAndHash[0]);
				ps.setString(3, saltAndHash[1]);
								
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
	


