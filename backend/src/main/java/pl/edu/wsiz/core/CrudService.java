package pl.edu.wsiz.core;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface CrudService <E extends BaseEntity> {

	ObjectMapper getMapper();
	
	Class<E> getPersistentClass();
	
	String listJson() throws Exception;
	
	String getJson(long id) throws Exception;
	
	E get(long id);
	
	List<E> list();
	
	String create (E entity) throws Exception;
	
	String update (E entity) throws Exception;
	
	void delete(long id);
	
	void delete(E entity);
	
	void preCreate(E entity) throws Exception;
	
	void preDelete(E entity);
	
	
}
