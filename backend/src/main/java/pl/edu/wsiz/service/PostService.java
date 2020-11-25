package pl.edu.wsiz.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.edu.wsiz.core.CoreMapper;
import pl.edu.wsiz.core.CrudServiceImpl;
import pl.edu.wsiz.mapper.PostMapper;
import pl.edu.wsiz.model.Post;
import pl.edu.wsiz.model.User;
import pl.edu.wsiz.repository.PostRepository;

@Service
@Transactional(rollbackOn = Exception.class)
public class PostService extends CrudServiceImpl<Post> {

	PostMapper postMapper = new PostMapper();
	@Autowired
	PostRepository postRepository;

	@Autowired
	UserService userService;

	@Override
	protected PostRepository getRepository() {
		return postRepository;
	}

	@Override
	public CoreMapper<Post> getMapper() {
		return postMapper;
	}

	@Override
	public void preCreate(Post entity) throws Exception {
		entity.setCreateDate(LocalDateTime.now());
		entity.setUser(userService.getLoggedUser());
	}

	@Override
	protected void preUpdate(Post entityToUpdate, Post newEntity) {
		entityToUpdate.setDescription(newEntity.getDescription());
		entityToUpdate.setImageURL(newEntity.getImageURL());
	}

	public List<Post> listByUserId(Long userId) {
		return getRepository().findAllByUserIdOrderByCreateDateDesc(userId);
	}
	
	public void like(Long id) {
		Post post = get(id);
		User loggedUser = userService.getLoggedUser();
		if(post.getUsersWhoLike().contains(loggedUser)) {
			post.getUsersWhoLike().remove(loggedUser);
		} else {
			post.getUsersWhoLike().add(loggedUser);
		}
	}

}
