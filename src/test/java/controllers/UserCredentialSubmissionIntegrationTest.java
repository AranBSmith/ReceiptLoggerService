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
	HashMap<String,String> userCredentials, badCredentialSet;

	
	@Before
	public void setup(){
		userCredSub = new UserCredentialSubmissionController();
		userCredentials = new HashMap<String,String>();
		userCredentials.put("aran.smith4@mail.dcu.ie","password");
		userCredentials.put("anemail@adomain.com","anotherpassword");
		
		badCredentialSet = new HashMap<String,String>();
		badCredentialSet.put("notanemail", "");
		badCredentialSet.put("", "password213");
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
