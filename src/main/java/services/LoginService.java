package services;

import dao.UserDAO;
import lombok.Data;
import model.LoginResponse;

@Data
public class LoginService {
	
	private UserDAO userLogin;
    private EmailService emailService;
    private LoginResponse loginResponse;
    
	public LoginService(){
		 userLogin = new UserDAO();
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
        	
            return loginResponse;
            
        } else {
            return userLogin.login(email, password);
        }
	}
}
