package controllers;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import services.CredentialsService;

public class UserCredentialSubmissionControllerTest {
	UserCredentialSubmissionController userCredSub;
	HashMap<String,String> userCredentials;
	CredentialsService credService;
	
	@Before
	public void setup(){
			
		userCredentials = new HashMap<String,String>();
		userCredentials.put("aran.smith47@mail.dcu.ie","password");
		userCredentials.put("anemail@adomain.com","anotherpassword");

		credService = new CredentialsService();
		when(credService.submitCredentials(userCredentials)).thenReturn(true);
		
		userCredSub = new UserCredentialSubmissionController();
		userCredSub.setCredService(credService);

	}
	
	@Test
	public void credentialSubmissionTest(){
		boolean response = userCredSub.credentialSubmission(userCredentials);
		assertTrue(response == true || response == false);
		assertEquals(response, true);
	}
}
