package services;

import java.util.HashMap;

import dao.UserDAO;
import lombok.Data;
import model.CredentialSubmissionResponse;

@Data
public class CredentialService {

	UserDAO userDAO;
	EmailService emailService;
	CredentialSubmissionResponse userCredResponse;
	
	public CredentialService(){
		userDAO = new UserDAO();
		emailService = new EmailService();
		userCredResponse = new CredentialSubmissionResponse();
	}
	
	public CredentialSubmissionResponse submitCredentials(HashMap<String, String> userCredentials) {
		// first check that every user email is valid.
		
		for(String email : userCredentials.keySet()){
			if(!emailService.isValidEmailAddress(email)){
				userCredResponse.setResponse(userCredResponse.getResponse() + 
						" This is an invalid email: " + 
						email + 
						" your user credentials submission will terminate and none of the "
						+ "credentials submitted will be found in the Database.");
				return userCredResponse;
			}
		}
		
		return userDAO.insertUserCredentials(userCredentials);
	}

}
