package controllers;

import java.util.HashMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import services.CredentialsService;

@Data
@RestController
public class UserCredentialSubmissionController {

	CredentialsService credService;
	
	@RequestMapping(value="credentialSubmission", method=RequestMethod.POST)
	public boolean credentialSubmission(HashMap<String, String> userCredentials) {
		// TODO Auto-generated method stub
		return credService.submitCredentials(userCredentials);
	}
	
}
