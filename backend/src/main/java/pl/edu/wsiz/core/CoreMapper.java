package pl.edu.wsiz.core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.core.GenericTypeResolver;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

public class CoreMapper<E> extends ObjectMapper {
	
	private static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";

	public static final long serialVersionUID = 1L;
	
	private Class<E> entityType;
	
	protected SimpleFilterProvider filterProvider = new SimpleFilterProvider();
	
	@SuppressWarnings("unchecked")
	public CoreMapper() {
		configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		entityType = (Class<E>) GenericTypeResolver.resolveTypeArgument(getClass(), CoreMapper.class);
		this.setFilterProvider(filterProvider);
		
		SimpleModule module = new SimpleModule();
		module.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
		module.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATE_FORMAT)));
		
		module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
		module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
		this.registerModule(module);
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
