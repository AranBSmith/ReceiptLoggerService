package controllers;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import model.LoginResponse;

public class LoginIntegrationTest {
	LoginController loginController;
	String password, email, nonexistentEmail;
	
	@Before
	public void setup(){
		loginController = new LoginController();
		email = "aran.smith47@mail.dcu.ie";
		nonexistentEmail = "admin@admin.com";
		password = "apassword";
	}
	
	@Test
	public void testIntegratedValidLogin(){
		LoginResponse loginResponse = loginController.login(email, password);
		System.out.println(loginResponse.getResponse());
		assertTrue(loginResponse.isSuccess());
	}
	
	@Test
	public void testIntegratedInvalidLogin(){
		assertFalse(loginController.login(email, "").isSuccess());
	}
	
	@Test
	public void testNonexistentEmail(){
		assertFalse(loginController.login(nonexistentEmail, password).isSuccess());
	}
	
	@Test
	public void testDoesntFailOnNull(){
		assertFalse(loginController.login(null, null).isSuccess());
	}
}
