package dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.LoginResponse;
public class UserDAOTest {
	
	UserDAO userDAO;
	String email, password, notAPassword;
	LoginResponse validLoginResponse, invalidLoginResponse;
	
	@Before
	public void setup(){
		userDAO = new UserDAO();
		email = "aran.smith47@mail.dcu.ie";
		password = "apassword";
		notAPassword = "123";
		validLoginResponse = new LoginResponse();
		validLoginResponse.setSuccess();
		invalidLoginResponse = new LoginResponse();
	}
	
	@Test
	public void testNotNull(){
		assertNotNull(userDAO.login(null, null));
		assertFalse(userDAO.login(null, null).equals(validLoginResponse));
	}
	
	@Test
	public void testSelectValidCredentials(){
		assertTrue(userDAO.login(email, password).equals(validLoginResponse));
	}
	
	@Test
	public void testSelectInvalidCredentials(){
		assertFalse(userDAO.login(email, notAPassword).equals(validLoginResponse));
	}
}
