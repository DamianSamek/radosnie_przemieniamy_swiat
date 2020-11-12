package pl.edu.wsiz.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.edu.wsiz.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	Role findByRole(String role);
}
