package services;

import dao.Cassandra;

public class LoginService {
	
	private Cassandra cassandraAccess;
    private EmailService emailService;
    
	public LoginService(){
		 cassandraAccess = new Cassandra();
	     emailService = new EmailService();
	}

	public boolean checkCredentials(String email, String password) {
		// verify if email is of a valid format.
        boolean validEmail = emailService.isValidEmailAddress(email);
        if(email.equals("") || password.equals("") || !validEmail ){
            return false;
        } else {
            return cassandraAccess.login(email, password);
        }
	}
}
