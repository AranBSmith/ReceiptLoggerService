package services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class EmailServiceTest {
	
	EmailService emailService;
	String validEmail, invalidEmail;
	
	@Before
	public void setUp(){
		emailService = new EmailService();
		validEmail = "aran.smith47@mail.dcu.ie";
		invalidEmail = "aran";
	}
	
	@Test
	public void checkEmailTest(){
		assertTrue(emailService.isValidEmailAddress(validEmail));
		assertFalse(emailService.isValidEmailAddress(invalidEmail));
	}
}
