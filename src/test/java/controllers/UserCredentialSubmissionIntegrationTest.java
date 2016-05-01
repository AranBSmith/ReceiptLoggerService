package controllers;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import model.CredentialSubmissionResponse;

public class UserCredentialSubmissionIntegrationTest {
	CredentialSubmissionResponse validUserCredSubResponse, invalidUserCredSubResponse, result;
	UserCredentialSubmissionController userCredSub;
	HashMap<String,String[]> userCredentials, badCredentialSet;

	
	@Before
	public void setup(){
		userCredSub = new UserCredentialSubmissionController();
		userCredentials = new HashMap<>();
		userCredentials.put("aran.smith4@mail.dcu.ie", new String[]{"password", "random"});
		userCredentials.put("anemail@adomain.com", new String[]{"anotherpassword", "random"});
		
		badCredentialSet = new HashMap<>();
		badCredentialSet.put("notanemail", new String[]{"", ""});
		badCredentialSet.put("", new String[]{"password213", ""});
	}
	
	@Test
	public void testValidIntegratedUserCredentialSubmission(){
		result = userCredSub.credentialSubmission(userCredentials);
		assertTrue(result.isSuccess());
	}
	
	@Test
	public void testInvalidIntegratedUserCredentialSubmission(){
		result = userCredSub.credentialSubmission(badCredentialSet);
		assertFalse(result.isSuccess());
	}
}
