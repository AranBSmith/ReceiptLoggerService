package controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import model.LoginResponse;
import services.LoginService;

@RestController
public class LoginController {
	LoginService loginService;
	
	@RequestMapping("/login")
    public LoginResponse greeting(
    		@RequestParam(value="email") String email,
    		@RequestParam(value="password") String password
    		) {
        //check credentials against service.
		//return response
		return new LoginResponse("test");
    }
	
}
