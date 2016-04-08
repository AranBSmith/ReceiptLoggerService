package services;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import dao.UserDAO;
import model.LoginResponse;

public class LoginServiceIntegratedTest {
	UserDAO userDAO;
	String email;
	String password;
	
	@Before
	public void setup(){
		userDAO = new UserDAO();
		email = "aran.smith47@mail.dcu.ie";
		password = "apassword";
	}
	
	
	@Test
	public void testIntegratedLoginService(){
		LoginResponse result = userDAO.login(email, password);
		assertTrue(result.getResponse().equals("valid"));
	}
}
