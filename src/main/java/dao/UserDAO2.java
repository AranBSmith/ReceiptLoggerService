package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.LoginResponse;

public class UserDAO2{
	
	private DataSource myDatasource;
	private LoginResponse loginResponse;

	public UserDAO2(){
		loginResponse = new LoginResponse();
		myDatasource = null;
		try{
			InitialContext ic = new InitialContext();
			Context xmlContext = (Context) ic.lookup("java:comp/env"); // thats everything from the context.xml and from the global configuration
			myDatasource = (DataSource) xmlContext.lookup("jdbc/ReceiptLoggerService");
		} catch(NamingException e){
			loginResponse.setResponse(loginResponse.getResponse() + "\n" + e.getMessage());
		}
	}

	public LoginResponse login(String email, String password) {
		
		if(myDatasource!=null) loginResponse.setResponse(loginResponse.getResponse() + "\n" + "datasource was successfully initialised");

		String sql = "select password from Users where email = ?";
		
		Connection conn = null;	
		
		try{
			loginResponse.setResponse(loginResponse.getResponse() + "\n" + "now trying to connect");
			conn = myDatasource.getConnection();
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

/*private DataSource getMySQLDataSource() {
Properties props = new Properties();
FileInputStream fis = null;
MysqlDataSource mysqlDS = null;
try {
    fis = new FileInputStream("db.properties");
    props.load(fis);
    mysqlDS = new MysqlDataSource();
    mysqlDS.setURL(props.getProperty("MYSQL_DB_URL"));
    mysqlDS.setUser(props.getProperty("MYSQL_DB_USERNAME"));
    mysqlDS.setPassword(props.getProperty("MYSQL_DB_PASSWORD"));
} catch (IOException e) {
    e.printStackTrace();
    loginResponse.setResponse(loginResponse.getResponse() + " " + e.getMessage());
}

if(mysqlDS == null){
	//this is running on the server so we must get our configuration this way instead, as db.properties
	// wont exist on the server
	mysqlDS = ds;
}

return mysqlDS;
}*/
	


