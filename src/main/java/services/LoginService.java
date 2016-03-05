package services;

import dao.Cassandra;
import model.LoginResponse;

public class LoginService {
	
	private Cassandra cassandraAccess;
    private EmailService emailService;
    private LoginResponse loginResponse;
    private static final String TAG = "LoginService";
    
	public LoginService(){
		 cassandraAccess = new Cassandra();
	     emailService = new EmailService();
	     loginResponse = new LoginResponse();
	}

	public LoginResponse checkCredentials(String email, String password) {
		// verify if email is of a valid format.
        boolean validEmail = emailService.isValidEmailAddress(email);
        if(email.equals("") || password.equals("") || !validEmail ){
        	loginResponse.setResponse("invalid");
            return loginResponse;
        } else {
            if(cassandraAccess.login(email, password)){
            	loginResponse.setResponse("valid");
            } else{
            	loginResponse.setResponse("invalid");
            }
        }
        System.out.println("login Response is: " + loginResponse.getResponse());
        return loginResponse;
	}
}
