package controllers;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

public class LoginIntegrationTest {
	LoginController loginController;
	String password, email;
	
	@Before
	public void setup(){
		loginController = new LoginController();
		email = "aran.smith47@mail.dcu.ie";
		password = "apassword";
	}
	
	@Test
	public void testIntegratedValidLogin(){
		assertTrue(loginController.login(email, password).isSuccess());
	}
	
	@Test
	public void testIntegratedInvalidLogin(){
		assertFalse(loginController.login(email, "").isSuccess());
	}
}
