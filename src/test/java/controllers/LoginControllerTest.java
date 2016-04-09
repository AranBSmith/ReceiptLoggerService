package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import model.LoginResponse;
import services.LoginService;

public class LoginControllerTest {
	LoginController loginController;
	String validEmail, validPassword, invalidEmail, invalidPassword;
	LoginResponse validLoginResponse, invalidLoginResponse, resultLoginResponse;
	LoginService loginService;
	
	@Before
	public void setup(){
		validLoginResponse = new LoginResponse();
		validLoginResponse.setSuccess();
		invalidLoginResponse = new LoginResponse();
		
		validEmail = "aran.smith47@mail.dcu.ie";
		validPassword = "password";
		invalidEmail = "aran";
		invalidPassword = "";
		
		loginService = mock(LoginService.class);
		when(loginService.checkCredentials(validEmail, validPassword)).thenReturn(validLoginResponse);
		when(loginService.checkCredentials(invalidEmail, validPassword)).thenReturn(invalidLoginResponse);
		when(loginService.checkCredentials(validEmail, invalidPassword)).thenReturn(invalidLoginResponse);
		when(loginService.checkCredentials(invalidEmail, invalidPassword)).thenReturn(invalidLoginResponse);
		when(loginService.checkCredentials(null, null)).thenReturn(invalidLoginResponse);
		
		loginController = new LoginController();
		loginController.setLoginService(loginService);
	}
	
	@Test
	public void loginTest(){
		resultLoginResponse = loginController.login(validEmail, validPassword);
		assertNotNull(resultLoginResponse);
		assertEquals(validLoginResponse.getResponse(), resultLoginResponse.getResponse());
		resultLoginResponse = loginController.login(invalidEmail, validPassword);
		assertEquals(invalidLoginResponse.getResponse(), resultLoginResponse.getResponse());
		resultLoginResponse = loginController.login(invalidEmail, validPassword);
		assertEquals(invalidLoginResponse.getResponse(), resultLoginResponse.getResponse());
		resultLoginResponse = loginController.login(validEmail, invalidPassword);
		assertEquals(invalidLoginResponse.getResponse(), resultLoginResponse.getResponse());
		resultLoginResponse = loginController.login(invalidEmail, invalidPassword);
		assertEquals(invalidLoginResponse.getResponse(), resultLoginResponse.getResponse());
	}
}
