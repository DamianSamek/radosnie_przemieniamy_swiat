package pl.edu.wsiz.mapper;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import pl.edu.wsiz.core.CoreMapper;
import pl.edu.wsiz.model.Role;



public class RoleMapper extends CoreMapper<Role> {

	private static final long serialVersionUID = 1L;
	
	public RoleMapper() {
		addFilter(Role.class, SimpleBeanPropertyFilter.filterOutAllExcept("id", "role"));
	}
}
