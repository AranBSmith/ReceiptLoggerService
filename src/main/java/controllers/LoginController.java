package controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import model.LoginResponse;
import services.LoginService;

@RestController
@Data
public class LoginController {
	private LoginService loginService;
	private LoginResponse loginResponse;
	
	public LoginController(){
		loginService = new LoginService();
		loginResponse = new LoginResponse();
		loginResponse.setResponse("valid");
	}
	
	@RequestMapping(value = "/login", method=RequestMethod.POST)
    public LoginResponse login(
    		@RequestParam(value="email") String email,
    		@RequestParam(value="password") String password){
		
		/*if(email.equals("aran.smith47@mail.dcu.ie") && password.equals("apassword"))
			return loginResponse;*/
		
		return loginService.checkCredentials(email, password);
    }
}
