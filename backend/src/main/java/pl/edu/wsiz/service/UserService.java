package pl.edu.wsiz.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import pl.edu.wsiz.core.CrudService;
import pl.edu.wsiz.model.User;

public interface UserService extends CrudService<User> {
	
	public String toJson(String username) throws JsonProcessingException;
	public User getLoggedUser();

	public void createUser(User user) throws Exception;
}
