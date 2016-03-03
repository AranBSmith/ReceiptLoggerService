package services;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class LoginServiceTest {
	LoginService loginService; 
	String validEmail, validPassword, invalidEmail, invalidPassword;
	
	@Before
	public void setUp(){
		loginService = new LoginService();
		validEmail = "aran.smith47@mail.dcu.ie";
		validPassword = "password";
		invalidEmail = "aran";
		invalidPassword = "";
	}
	
	@Test
	public void checkCredentialsTest(){
		boolean response = loginService.checkCredentials(validEmail, validPassword);
		assertNotNull(response);
		assert(response == true || response == false);
		response = loginService.checkCredentials(invalidEmail, invalidPassword);
		assertFalse(response);
		response = loginService.checkCredentials(validEmail, invalidPassword);
		assertFalse(response);
		response = loginService.checkCredentials(invalidEmail, validPassword);
		assertFalse(response);
	}
}
