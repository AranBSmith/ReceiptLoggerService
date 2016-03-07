package services;

import dao.Cassandra;
import lombok.Data;
import model.LoginResponse;

@Data
public class LoginService {
	
	private Cassandra cassandraAccess;
    private EmailService emailService;
    private LoginResponse loginResponse;
    
	public LoginService(){
		 cassandraAccess = new Cassandra();
	     emailService = new EmailService();
	     loginResponse = new LoginResponse();
	}

	public LoginResponse checkCredentials(String email, String password) {
		// verify if email is of a valid format.
        boolean validEmail = false;
        validEmail = emailService.isValidEmailAddress(email);
        if(
        		email == null 
        		|| password == null
        		|| email.equals("") 
        		|| password.equals("") 
        		|| !validEmail ){
        	
        	loginResponse.setResponse("invalid");
            return loginResponse;
            
        } else {
            if(cassandraAccess.login(email, password)){
            	loginResponse.setResponse("valid");
            } else{
            	loginResponse.setResponse("invalid");
            }
        }

        return loginResponse;
	}
}
