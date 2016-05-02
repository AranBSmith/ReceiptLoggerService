package controllers;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import model.CredentialSubmissionResponse;
import services.CredentialService;

/**
 * Rest Controller used to submit a batch of user credentials. This class makes
 * use of the CredentialService class to submit batch user credentials.
 * 
 * @author Aran
 *
 */
@Data
@RestController
public class UserCredentialSubmissionController {

	CredentialService credService;
	
	/**
	 * Creates an instance of the Credential Service.
	 */
	public UserCredentialSubmissionController(){
		credService = new CredentialService();
	}
	
	/**
	 * Used when the /credentialSubmission URL is called and provided with the
	 * parameters below, it will pass the parameters provided to the
	 * CredentialService.
	 * 
	 * @param userCredentials A hashmap<String, String[]>, the key being a users
	 * email address, which maps to a String array which contains in its first 
	 * element a hashed password based on SHA-512 and converted to a String in 
	 * Base64, the second element of the array is the salt for the corresponding
	 * hashed password.
	 * @return CredentialSubmission Response object specifying the status of the 
	 * submission, if it was unsuccessful the response will contain the messages
	 * of any exceptions thrown upon trying to submit user credentials.
	 */
	@RequestMapping(value="/credentialSubmission", method=RequestMethod.POST)
	public CredentialSubmissionResponse credentialSubmission(
			@RequestParam("userCredentials") HashMap<String, String[]> userCredentials) {
		return credService.submitCredentials(userCredentials);
	}
	
}
