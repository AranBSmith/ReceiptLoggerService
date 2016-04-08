package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import model.LoginResponse;

public class UserDAO extends DAO {

	private DataSource dataSource;
	private LoginResponse loginResponse;

	public UserDAO(){
		this.dataSource = super.getMySQLDataSource();
		loginResponse = new LoginResponse();
	}
	
	public LoginResponse login(String email, String password) {
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
			loginResponse.setResponse(e.getMessage());
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
	


