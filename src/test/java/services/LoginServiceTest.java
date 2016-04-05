package services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import dao.UserDAO;
import model.LoginResponse;

public class LoginServiceTest {
	UserDAO userDAO;
	String validEmail, validPassword, invalidEmail, invalidPassword;
	LoginService loginService;
	EmailService emailService;
	LoginResponse validLoginResponse, invalidLoginResponse, resultLoginResponse;
	
	@Before
	public void setUp(){
		validLoginResponse = new LoginResponse();
		validLoginResponse.setResponse("valid");
		invalidLoginResponse = new LoginResponse();
		invalidLoginResponse.setResponse("invalid");
		
		validEmail = "aran.smith47@mail.dcu.ie";
		validPassword = "password";
		invalidEmail = "aran";
		invalidPassword = "";
		
		userDAO = mock(UserDAO.class);
		when(userDAO.login(validEmail, validPassword)).thenReturn(true);
		when(userDAO.login(invalidEmail, validPassword)).thenReturn(false);
		when(userDAO.login(validEmail, invalidPassword)).thenReturn(false);
		when(userDAO.login(invalidEmail, invalidPassword)).thenReturn(false);
		when(userDAO.login(null, null)).thenReturn(false);
		
		emailService = mock(EmailService.class);
		when(emailService.isValidEmailAddress(validEmail)).thenReturn(true);
		when(emailService.isValidEmailAddress(invalidEmail)).thenReturn(false);

		
		loginService = new LoginService();
		loginService.setUserLogin(userDAO);
		loginService.setEmailService(emailService);
	}
	
	@Test
	public void checkCredentialsTest(){
		LoginResponse response = loginService.checkCredentials(validEmail, validPassword);
		assertNotNull(response);
		assertEquals(validLoginResponse.getResponse(), response.getResponse());	
		response = loginService.checkCredentials(invalidEmail, invalidPassword);
		assertEquals(response.getResponse(),invalidLoginResponse.getResponse());
		response = loginService.checkCredentials(validEmail, invalidPassword);
		assertEquals(response.getResponse(),invalidLoginResponse.getResponse());
		response = loginService.checkCredentials(invalidEmail, validPassword);
		assertEquals(response.getResponse(),invalidLoginResponse.getResponse());
		response = loginService.checkCredentials("", "");
		assertEquals(response.getResponse(),invalidLoginResponse.getResponse());
		response = loginService.checkCredentials(null, null);
		assertEquals(response.getResponse(),invalidLoginResponse.getResponse());
	}
}
