package services;

import java.util.HashMap;

import dao.UserDAO;
import lombok.Data;
import model.CredentialSubmissionResponse;

/**
 * Credential Service class used to verifty user credentials before submitting
 * them to the UserDAO class.
 * 
 * @author Aran
 *
 */
@Data
public class CredentialService {

	UserDAO userDAO;
	EmailService emailService;
	CredentialSubmissionResponse userCredResponse;
	
	/**
	 * Creates an instance of the UserDAO, EmailService and 
	 * CredentialSubmissionResponse classes
	 */
	public CredentialService(){
		userDAO = new UserDAO();
		emailService = new EmailService();
		userCredResponse = new CredentialSubmissionResponse();
	}
	
	/**
	 * Checks for nulls in the arguments passed, verifies that the String array
	 * is of the correct length (2), and verifies if the email provided is 
	 * authentic. If the arguments pass the check the UserDAO class is used in 
	 * order to insert a batch set of user credentials data.
	 * 
	 * @param userCredentials Hashmap of with key being the email mapped to a 
	 * String array containing the hashed SHA-512 password in its first element
	 * and the corresponding salt for that hashed password. 
	 * @return CredentialSubmission Response object specifying the status of the 
	 * submission, if it was unsuccessful the response will contain the messages
	 * of any exceptions thrown upon trying to submit user credentials.
	 */
	public CredentialSubmissionResponse submitCredentials(HashMap<String, String[]> userCredentials) {
		
		// first check that every user email is valid.
		try {
			for(String email : userCredentials.keySet()){
				if(!emailService.isValidEmailAddress(email) && 
						userCredentials.get(email)[0] != null && 
						userCredentials.get(email)[1] != null &&
						userCredentials.get(email).length == 2){
					
					userCredResponse.appendMessage("This is an invalid credential submission: " + 
							email + 
							" your user credentials submission will terminate and none of the "
							+ "credentials submitted will be found in the Database.");
					
					return userCredResponse;
				}
			}
		} catch (NullPointerException e){
			userCredResponse.appendMessage("null values were submitted to this service, this set of "
					+ "credentials has been refused by the web service");
			return userCredResponse;
		}
		
		return userDAO.insertUserCredentials(userCredentials);
	}

}
