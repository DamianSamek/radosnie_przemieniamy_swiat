package pl.edu.wsiz.repository;

import pl.edu.wsiz.core.CoreRepository;
import pl.edu.wsiz.model.User;

public interface UserRepository extends CoreRepository<User, Long> {

	User findByUsername(String username);

	Boolean existsByUsername(String username);
}
