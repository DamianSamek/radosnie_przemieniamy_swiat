package pl.edu.wsiz.core;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.core.GenericTypeResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

@Transactional(rollbackOn = Exception.class)
public abstract class CrudServiceImpl<E extends BaseEntity> implements CrudService<E> {

	public abstract CoreMapper<E> getMapper();

	protected abstract CoreRepository<E, Long> getRepository();

	private Class<E> persistentClass;

	@SuppressWarnings("unchecked")
	public CrudServiceImpl() {
		this.persistentClass = (Class<E>) GenericTypeResolver.resolveTypeArguments(getClass(), CrudServiceImpl.class)[0];
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
		E entityToUpdate = get(entity.getId());
		preUpdate(entityToUpdate, entity);
		getRepository().saveAndFlush(entityToUpdate);
		return getMapper().writeValueAsString(entityToUpdate);
	}

	public void delete(long id) {
		E entity = getRepository().findById(id).orElse(null);
		delete(entity);

	}

	public void delete(E entity) {
		preDelete(entity);
		getRepository().delete(entity);
	}

	public Class<E> getPersistentClass() {
		return persistentClass;
	}

	public void preCreate(E entity) throws Exception {
		return;
	}
	
	protected void preUpdate(E entityToUpdate, E newEntity) {
		
	}
	
	public void preDelete(E entity) {}
}
