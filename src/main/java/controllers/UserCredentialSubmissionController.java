package controllers;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import services.CredentialsService;

@Data
@RestController
public class UserCredentialSubmissionController {

	CredentialsService credService;
	
	public boolean credentialSubmission(HashMap<String, String> userCredentials) {
		// TODO Auto-generated method stub
		return credService.submitCredentials(userCredentials);
	}
	
}
