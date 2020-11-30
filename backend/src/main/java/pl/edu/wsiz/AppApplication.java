package pl.edu.wsiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import pl.edu.wsiz.model.Role;
import pl.edu.wsiz.model.User;
import pl.edu.wsiz.repository.RoleRepository;
import pl.edu.wsiz.service.EncryptionService;
import pl.edu.wsiz.service.UserService;

@SpringBootApplication
@EnableJpaRepositories("pl.edu.wsiz.repository")
public class AppApplication implements ApplicationListener<ApplicationEvent> {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	EncryptionService encryptionService;

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(AppApplication.class, args);
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Transactional(rollbackFor = Exception.class)
	private void init() throws Exception {
		Role admin = createRole("admin");
		createUser("admin1", "Admin1@", admin);
		Role user = createRole("user");
		createUser("user1", "User1@", user);
	}

	private Role createRole(String roleName) {
		Role role = new Role();
		role.setRole(roleName);
		roleRepository.save(role);
		return role;
	}

	private User createUser(String username, String password, Role role) throws Exception {
		User user = new User();
		user.setUsername(username);
		user.setPassword(encryptionService.encode(password));
		user.setRole(role);
		userService.create(user);
		return user;
	}

}
