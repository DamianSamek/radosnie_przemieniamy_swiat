package pl.edu.wsiz.crud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl.edu.wsiz.core.CrudServiceImpl;
import pl.edu.wsiz.crud.service.UserCrudService;
import pl.edu.wsiz.mapper.UserMapper;
import pl.edu.wsiz.model.User;
import pl.edu.wsiz.repository.UserRepository;



public class UserCrudServiceImpl extends CrudServiceImpl<User> implements UserCrudService {

	@Autowired
	private UserRepository userRepository;

	UserMapper mapper = new UserMapper();

	@Override
	public ObjectMapper getMapper() {
		return mapper;
	}

	@Override
	protected UserRepository getRepository() {
		return userRepository;
	}

}
