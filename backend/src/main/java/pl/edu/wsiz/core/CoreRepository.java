package pl.edu.wsiz.core;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CoreRepository<T, ID> extends JpaRepository<T, ID> {

}
