package pl.edu.wsiz.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.wsiz.core.CoreMapper;
import pl.edu.wsiz.core.CoreRepository;
import pl.edu.wsiz.core.CrudServiceImpl;
import pl.edu.wsiz.mapper.ArticleMapper;
import pl.edu.wsiz.model.Article;
import pl.edu.wsiz.repository.ArticleRepository;

@Service
@Transactional(rollbackOn = Exception.class)
public class ArticleService extends CrudServiceImpl<Article> {

	ArticleMapper mapper = new ArticleMapper();

	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	UserService userService;

	@Override
	public CoreMapper<Article> getMapper() {
		return mapper;
	}

	@Override
	protected CoreRepository<Article, Long> getRepository() {
		return articleRepository;
	}

	@Override
	public void preCreate(Article entity) throws Exception {
		entity.setCreateDate(LocalDateTime.now());
		entity.setUser(userService.getLoggedUser());
	}

	@Override
	protected void preUpdate(Article entityToUpdate, Article newEntity) {
		entityToUpdate.setContent(newEntity.getContent());
		entityToUpdate.setImageURL(newEntity.getImageURL());
		entityToUpdate.setTitle(newEntity.getTitle());
	}

}
