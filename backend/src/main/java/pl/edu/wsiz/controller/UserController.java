package pl.edu.wsiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.wsiz.core.CrudController;
import pl.edu.wsiz.model.User;
import pl.edu.wsiz.service.UserService;



@RestController
@RequestMapping("user")
public class UserController extends CrudController<User, UserService> {

	@Autowired
	UserService userService;

	@Override
	protected UserService getService() {
		return userService;
	}

}
