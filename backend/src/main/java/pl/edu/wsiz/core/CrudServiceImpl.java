package pl.edu.wsiz.core;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.core.GenericTypeResolver;
import org.springframework.util.ReflectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@Transactional(rollbackOn = Exception.class)
public abstract class CrudServiceImpl<E extends BaseEntity> implements CrudService<E> {

	@Override
	public abstract ObjectMapper getMapper();

	protected abstract CoreRepository<E, Long> getRepository();
	
	private Class<E> persistentClass;
	
	@SuppressWarnings("unchecked")
	public CrudServiceImpl() {
		this.persistentClass = (Class<E>) GenericTypeResolver.resolveTypeArguments(getClass(), CrudServiceImpl.class)[0];
	}

	@Override
	public E get(long id) {
		E entity = getRepository().findById(id).orElse(null);
		return entity;
	}
	
	@Override
	public String getJson(long id) throws Exception {
		E entity = get(id);
		return getMapper().writeValueAsString(entity);
	}

	@Override
	public List<E> list() {
		List<E> list = getRepository().findAll();
		return list;
	}
	
	@Override
	public String listJson() throws Exception {
		List<E> list = list();
		return getMapper().writeValueAsString(list);
	}

	@Override
	public String create(E entity) throws Exception {
		preCreate(entity);
		getRepository().save(entity);
		return getMapper().writeValueAsString(entity);
	}

	@Override
	public String update(E entity) throws Exception {
		getRepository().saveAndFlush(entity);
		return getMapper().writeValueAsString(entity);
	}

	@Override
	public void delete(long id) {
		E entity = getRepository().findById(id).orElse(null);
		delete(entity);

	}

	@Override
	public void delete(E entity) {
		getRepository().delete(entity);
	}
	
	@Override
	public Class<E> getPersistentClass() {
		return persistentClass;
	}
	
	@Override
	public void preCreate(E entity) throws Exception {
		return;
	}

}
