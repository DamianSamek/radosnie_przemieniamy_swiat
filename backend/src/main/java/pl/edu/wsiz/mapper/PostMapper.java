package pl.edu.wsiz.mapper;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import pl.edu.wsiz.core.CoreMapper;
import pl.edu.wsiz.field.PostField;
import pl.edu.wsiz.field.UserField;
import pl.edu.wsiz.model.Post;
import pl.edu.wsiz.model.User;

public class PostMapper extends CoreMapper<Post> {

	private static final long serialVersionUID = 1L;

	public PostMapper() {
		addFilter(Post.class, SimpleBeanPropertyFilter.serializeAllExcept(PostField.USERS_WHO_LIKE));
		addFilter(User.class, SimpleBeanPropertyFilter.filterOutAllExcept(UserField.NAME));
	}
}
