package controllers;

import static org.junit.Assert.assertTrue;

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
		//assertTrue(loginController.login(email, password).getResponse().equals("valid"));
	}
	
	@Test
	public void testIntegratedInvalidLogin(){
		assertTrue(loginController.login(email, "").getResponse().equals("invalid"));
	}
}
