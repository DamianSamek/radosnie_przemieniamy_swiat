package pl.edu.wsiz.core;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class CrudController<E extends BaseEntity, S extends CrudService<E>> {

	public static final String CREATE = "/create";
	public static final String UPDATE = "/update";
	public static final String DELETE = "/delete/{id}";
	public static final String DETAILS = "/details/{id}";
	public static final String LIST = "list";

	ObjectMapper mapper;

	protected S getService() {
		return null;
	};

	@PostConstruct
	public void postConstruct() {
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter(CoreMapper.getFilterName(getService().getPersistentClass()),
				SimpleBeanPropertyFilter.filterOutAllExcept("id"));
		mapper = new ObjectMapper();
		mapper.setFilterProvider(filterProvider);
	}

	@RequestMapping(value = CREATE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> create(@RequestBody E entity) throws Exception {
		String response = getService().create(entity);
		return ResponseEntity.ok().body(response);
	}

	@RequestMapping(value = DETAILS, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> detailsGet(@PathVariable long id) throws Exception {
		String entity = getService().getJson(id);
		return ResponseEntity.ok().body(entity);
	}

	@RequestMapping(value = LIST, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> listGet() throws Exception {
		String list = getService().listJson();
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = DELETE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> delete(@PathVariable long id) throws Exception {
		getService().delete(id);
		return ResponseEntity.ok().body("");
	}

}
