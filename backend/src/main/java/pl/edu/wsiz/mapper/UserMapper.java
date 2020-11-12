package pl.edu.wsiz.mapper;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import pl.edu.wsiz.core.CoreMapper;
import pl.edu.wsiz.model.Role;
import pl.edu.wsiz.model.User;

public class UserMapper extends CoreMapper<User> {

	private static final long serialVersionUID = 1L;

	public UserMapper() {
		addFilter(User.class, SimpleBeanPropertyFilter.filterOutAllExcept("id", "username", "role"));
		addFilter(Role.class, SimpleBeanPropertyFilter.filterOutAllExcept("role"));
	}
}
