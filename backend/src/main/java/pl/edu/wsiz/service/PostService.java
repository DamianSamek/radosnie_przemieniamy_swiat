package pl.edu.wsiz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import pl.edu.wsiz.core.CrudService;
import pl.edu.wsiz.model.Post;
import pl.edu.wsiz.repository.PostRepository;

public class PostService extends CrudService<Post> {

	@Autowired
	PostRepository postRepository;

	@Override
	protected PostRepository getRepository() {
		return postRepository;
	}

	public List<Post> getUserPosts(Long userId) {
		return getRepository().findAllByUserId(userId);
	}

}
