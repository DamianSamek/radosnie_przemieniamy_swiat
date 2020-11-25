package pl.edu.wsiz.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import pl.edu.wsiz.core.ApplicationException;
import pl.edu.wsiz.core.CrudService;
import pl.edu.wsiz.model.Post;
import pl.edu.wsiz.model.Role;
import pl.edu.wsiz.model.User;
import pl.edu.wsiz.repository.RoleRepository;
import pl.edu.wsiz.repository.UserRepository;
import pl.edu.wsiz.util.ObjectUtilsExt;

@Service
public class UserService extends CrudService<User> {

	@Autowired
	EncryptionService encryptionService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	@Override
	protected UserRepository getRepository() {
		return userRepository;
	}

	public String toJson(String username) throws JsonProcessingException {
		User user = getRepository().findByUsername(username);
		String result = "";
		if (user != null) {
			result = getMapper().writeValueAsString(user);
		}
		return result;
	}

	public User getLoggedUser() {
		String loggedUserName = ObjectUtilsExt
				.get(() -> SecurityContextHolder.getContext().getAuthentication().getName());
		if (loggedUserName == null) {
			return null;
		}
		return getRepository().findByUsername(loggedUserName);

	}

	@Transactional(rollbackFor = Exception.class)
	public void createUser(User user) throws Exception {

		if (getRepository().existsByUsername(user.getUsername())) {
			throw new ApplicationException("Uzytkownik o podanej nazwie juz istnieje");
		}
		String password = encryptionService.encode(user.getPassword());
		user.setPassword(password);

		String userRole = user.getRole().getRole();
		Role role = roleRepository.findByRole(user.getRole().getRole());
		user.setRole(role);
		create(user);

		switch (userRole) {
		case "CLIENT": {

			break;
		}
		case "SERVICE_PROVIDER": {
			break;
		}
		}
	}

}
