package services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import dao.UserDAO;
import model.CredentialSubmissionResponse;

public class CredentialServiceTest {

	CredentialService credentialService;
	UserDAO userDAO;
	HashMap<String,String[]> userCredentials, badCredentialSet;
	CredentialSubmissionResponse successfulCredSubResponse;
	
	@Before
	public void setup(){
		userCredentials = new HashMap<>();
		userCredentials.put("aran.smith47@mail.dcu.ie", new String[]{"password", "random"});
		userCredentials.put("anemail@adomain.com", new String[]{"anotherpassword", "random"});
		
		badCredentialSet = new HashMap<>();
		badCredentialSet.put("notanemail", new String[]{null, null});
		badCredentialSet.put("", new String[]{"password213", "random"});
		badCredentialSet.put(null, null);

		successfulCredSubResponse = new CredentialSubmissionResponse();
		successfulCredSubResponse.setSuccess();
		
		userDAO = mock(UserDAO.class);
		when(userDAO.insertUserCredentials(userCredentials)).thenReturn(successfulCredSubResponse);
		when(userDAO.insertUserCredentials(badCredentialSet)).thenReturn(new CredentialSubmissionResponse());
		
		credentialService = new CredentialService();
		credentialService.setUserDAO(userDAO);
	}
	
	@Test
	public void testValidCredentialSubmission(){
		CredentialSubmissionResponse result = credentialService.submitCredentials(userCredentials);
		assertNotNull(result);
		assertTrue(result.isSuccess());
	}
	
	@Test 
	public void testInvlaidCredentialSubmission(){
		CredentialSubmissionResponse result = credentialService.submitCredentials(badCredentialSet);
		assertNotNull(result);
		assertFalse(result.isSuccess());
	}
}
