package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import model.LoginResponse;

public class UserDAO2 extends DAO {

	private DataSource dataSource;
	private LoginResponse loginResponse;

	public UserDAO2(){
		this.dataSource = super.getMySQLDataSource();
		loginResponse = new LoginResponse();
	}
	
	public LoginResponse login(String email, String password) {
		String sql = "select password from Users where email = ?";
		
		Connection conn = null;	
		
		loginResponse.setResponse(loginResponse.getResponse() + "\n" + "sql statement is: " + sql );
		//loginResponse.setResponse(loginResponse.getResponse() + "\n" + "conn is " + conn.toString());
		
		try{
			loginResponse.setResponse(loginResponse.getResponse() + "\n" + "now trying to connect");
			conn = dataSource.getConnection();
			loginResponse.setResponse(loginResponse.getResponse() + "\n" + "conn = dataSource.getConnection() intialised conn to" 
			+ conn.toString() + " and has schema: " + conn.getSchema());
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			loginResponse.setResponse(loginResponse.getResponse() + "\n" + "prepared statement is: " + ps.toString());
			ResultSet resultSet = ps.executeQuery();
			loginResponse.setResponse(loginResponse.getResponse() + "\n" + "result set fetch size is: " + resultSet.getFetchSize());

			
			if(resultSet.next()){
				// there is a result, check if the password is the same.
				String testPassword = resultSet.getString("password");
				if (password.equals(testPassword)){
					loginResponse.setResponse("valid");
					return loginResponse;
				}
			}
			loginResponse.setResponse("invalid");
			return loginResponse;
		} catch(SQLException e){
			loginResponse.setResponse(e.getMessage() + " " + e.getSQLState());
			return loginResponse;
		} catch (NullPointerException e){
			loginResponse.setResponse(loginResponse.getResponse() + "\nThere was a null pointer exception when trying to interact with the DB"
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
}
	


