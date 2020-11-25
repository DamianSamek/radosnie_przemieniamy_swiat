package pl.edu.wsiz.core;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.core.GenericTypeResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

@Transactional(rollbackOn = Exception.class)
public class CrudService<E extends BaseEntity> {

	public ObjectMapper getMapper() {
		return null;
	};

	protected CoreRepository<E, Long> getRepository() {
		return null;
	};

	private Class<E> persistentClass;

	@SuppressWarnings("unchecked")
	public CrudService() {
		this.persistentClass = (Class<E>) GenericTypeResolver.resolveTypeArguments(getClass(), CrudService.class)[0];
	}

	public E get(long id) {
		E entity = getRepository().findById(id).orElse(null);
		return entity;
	}

	public String getJson(long id) throws Exception {
		E entity = get(id);
		return getMapper().writeValueAsString(entity);
	}

	public List<E> list() {
		List<E> list = getRepository().findAll();
		return list;
	}

	public String listJson() throws Exception {
		List<E> list = list();
		return getMapper().writeValueAsString(list);
	}

	public String create(E entity) throws Exception {
		preCreate(entity);
		getRepository().save(entity);
		return getMapper().writeValueAsString(entity);
	}

	public String update(E entity) throws Exception {
		getRepository().saveAndFlush(entity);
		return getMapper().writeValueAsString(entity);
	}

	public void delete(long id) {
		E entity = getRepository().findById(id).orElse(null);
		delete(entity);

	}

	public void delete(E entity) {
		getRepository().delete(entity);
	}

	public Class<E> getPersistentClass() {
		return persistentClass;
	}

	public void preCreate(E entity) throws Exception {
		return;
	}

}
