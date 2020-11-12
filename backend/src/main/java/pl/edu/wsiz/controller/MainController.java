package pl.edu.wsiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.wsiz.model.User;
import pl.edu.wsiz.service.UserService;

@RestController
@RequestMapping("/")
public class MainController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user) throws Exception {
		userService.createUser(user);		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
