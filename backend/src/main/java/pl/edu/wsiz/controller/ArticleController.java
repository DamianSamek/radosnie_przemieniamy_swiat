package pl.edu.wsiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.edu.wsiz.core.CrudController;
import pl.edu.wsiz.model.Article;
import pl.edu.wsiz.service.ArticleService;

@RestController
@RequestMapping("article")
public class ArticleController extends CrudController<Article, ArticleService> {

	@Autowired
	ArticleService articleService;

	@Override
	protected ArticleService getService() {
		return articleService;
	}

}
