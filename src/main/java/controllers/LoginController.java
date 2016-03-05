package controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import model.LoginResponse;
import services.LoginService;

@RestController
@Data
public class LoginController {
	private LoginService loginService;
	
	public LoginController(){}
	
	@RequestMapping("/login")
    public LoginResponse login(
    		@RequestParam(value="email") String email,
    		@RequestParam(value="password") String password
    		) {
        //check credentials against service.
		//return response
		
		return loginService.checkCredentials(email, password);
    }
}
