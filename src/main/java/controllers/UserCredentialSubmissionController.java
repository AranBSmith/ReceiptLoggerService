package controllers;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import model.CredentialSubmissionResponse;
import services.CredentialService;

@Data
@RestController
public class UserCredentialSubmissionController {

	CredentialService credService;
	
	public UserCredentialSubmissionController(){
		credService = new CredentialService();
	}
	
	@RequestMapping(value="credentialSubmission", method=RequestMethod.POST)
	public CredentialSubmissionResponse credentialSubmission(HashMap<String, String> userCredentials) {
		return credService.submitCredentials(userCredentials);
	}
	
}
