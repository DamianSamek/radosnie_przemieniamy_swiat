package pl.edu.wsiz.mapper;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import pl.edu.wsiz.core.CoreMapper;
import pl.edu.wsiz.field.UserField;
import pl.edu.wsiz.model.Article;
import pl.edu.wsiz.model.User;

public class ArticleMapper extends CoreMapper<Article> {

	private static final long serialVersionUID = 1L;

	public ArticleMapper() {
		addFilter(Article.class, SimpleBeanPropertyFilter.serializeAll());
		addFilter(User.class, SimpleBeanPropertyFilter.filterOutAllExcept(UserField.ID, UserField.NAME));
	}
}
