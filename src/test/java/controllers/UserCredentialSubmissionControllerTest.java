package controllers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import model.CredentialSubmissionResponse;
import services.CredentialService;

public class UserCredentialSubmissionControllerTest {
	UserCredentialSubmissionController userCredSubController;
	HashMap<String,String[]> userCredentials, badCredentialSet;
	CredentialService credService;
	CredentialSubmissionResponse successfulCredSubResponse;
	
	@Before
	public void setup(){
			
		userCredentials = new HashMap<>();
		userCredentials.put("aran.smith47@mail.dcu.ie", new String[]{"password", "random"});
		userCredentials.put("anemail@adomain.com", new String[]{"anotherpassword", "random"});
		
		badCredentialSet = new HashMap<>();
		badCredentialSet.put("notanemail", new String[]{"",""});
		badCredentialSet.put("", new String[]{"password213",""});

		successfulCredSubResponse = new CredentialSubmissionResponse();
		successfulCredSubResponse.setSuccess();
		
		credService = mock(CredentialService.class);
		when(credService.submitCredentials(userCredentials)).thenReturn(successfulCredSubResponse);
		when(credService.submitCredentials(badCredentialSet)).thenReturn(new CredentialSubmissionResponse());
		
		userCredSubController = new UserCredentialSubmissionController();
		userCredSubController.setCredService(credService);

	}
	
	@Test
	public void validCredentialSubmissionTest(){
		CredentialSubmissionResponse response = userCredSubController.credentialSubmission(userCredentials);
		assertTrue(response.isSuccess() || !response.isSuccess());
		assertEquals(response.isSuccess(), true);
	}
	
	@Test
	public void invalidCredentialSubmissionTest(){
		CredentialSubmissionResponse response = userCredSubController.credentialSubmission(badCredentialSet);
		assertFalse(response.isSuccess());
	}
}
