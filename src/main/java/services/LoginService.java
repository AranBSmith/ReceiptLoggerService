package services;

import dao.UserDAO;
import lombok.Data;
import model.LoginResponse;

/**
 * Service class fulfilling the business logic required to validate a login.
 * 
 * @author Aran
 *
 */
@Data
public class LoginService {
	
	private UserDAO userLogin;
    private EmailService emailService;
    private LoginResponse loginResponse;
    
	/**
	 * This class makes us of the UserDAO, EmailService and LoginResponse classes.
	 */
	public LoginService(){
		 userLogin = new UserDAO();
	     emailService = new EmailService();
	     loginResponse = new LoginResponse();
	}

	/**
	 * Inspects the arguments passed before passing data to the UserDAO, this
	 * includes verifying if an actual email has been passed and making sure
	 * no null values were passed.
	 * 
	 * @param email
	 * @param password
	 * @return LoginResponse class specifying the status of the login, shall 
	 * contain any exceptions thrown during the process of logging in.
	 */
	public LoginResponse checkCredentials(String email, String password) {
		// verify if email is of a valid format.
        if(    email == null 
    		|| password == null
    		|| email.equals("") 
    		|| password.equals("") ){
            return loginResponse;
        }
        else if(!emailService.isValidEmailAddress(email))
        	return loginResponse;
        else
        	return userLogin.login(email, password);
	}
}
