package controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import model.LoginResponse;
import services.LoginService;

/**
 * This RestController class shall be associated with validating a user's login.
 * @author Aran
 */

@RestController
@Data
public class LoginController {
	private LoginService loginService;
	
	/**
	 * Constructor creates its own instance of the LoginService required
	 * to validate a user's credentials.
	 */
	public LoginController(){
		loginService = new LoginService();
	}
	
	/**
	 * Upon posting to the /login URL with parameters email and password,
	 * this function login is called. It will use the LoginService object
	 * created by this controller to authenticate a user's credentials.
	 * 
	 * @param email A user's email address
	 * @param password A user's password
	 * @return LoginResponse object containing success at the start of it's response 
	 * iff the login was successful. Given the response was not successful
	 * the response variable inside this response object will contain information, 
	 * and exceptions thrown during the login process.
	 */
	@RequestMapping(value = "/login", method=RequestMethod.POST)
    public LoginResponse login(
    		@RequestParam(value="email") String email,
    		@RequestParam(value="password") String password){
		return loginService.checkCredentials(email, password);
    }
}
