package services;

import dao.UserDAO2;
import lombok.Data;
import model.LoginResponse;

@Data
public class LoginService {
	
	private UserDAO2 userLogin;
    private EmailService emailService;
    private LoginResponse loginResponse;
    
	public LoginService(){
		 userLogin = new UserDAO2();
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
            return userLogin.login(email, password);
        }
	}
}
