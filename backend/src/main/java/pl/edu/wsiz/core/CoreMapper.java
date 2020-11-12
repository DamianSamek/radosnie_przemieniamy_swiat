package pl.edu.wsiz.core;

import org.springframework.core.GenericTypeResolver;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class CoreMapper<E> extends ObjectMapper {

	public static final long serialVersionUID = 1L;
	
	private Class<E> entityType;
	
	protected SimpleFilterProvider filterProvider = new SimpleFilterProvider();
	
	@SuppressWarnings("unchecked")
	public CoreMapper() {
		configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		entityType = (Class<E>) GenericTypeResolver.resolveTypeArgument(getClass(), CoreMapper.class);
		this.setFilterProvider(filterProvider);
	}
	
	public Class<?> getEntityType() {
		return entityType;
	}
	
	protected void addFilter(Class<? extends BaseEntity> clazz, SimpleBeanPropertyFilter filter) {
		filterProvider.addFilter(getFilterName(clazz), filter);
	}
	
	public static <E extends BaseEntity> String getFilterName(Class<E> clazz) {
		JsonFilter jsonFilter = clazz.getAnnotation(JsonFilter.class);
		return jsonFilter.value();
	}
}
