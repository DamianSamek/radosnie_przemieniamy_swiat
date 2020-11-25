package pl.edu.wsiz.mapper;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import pl.edu.wsiz.core.CoreMapper;
import pl.edu.wsiz.model.Post;

public class PostMapper extends CoreMapper<Post>{ 

	private static final long serialVersionUID = 1L;
	{
		addFilter(Post.class, SimpleBeanPropertyFilter.serializeAllExcept("user"));
	}
}
