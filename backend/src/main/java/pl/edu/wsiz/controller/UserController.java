package pl.edu.wsiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import pl.edu.wsiz.core.CrudController;
import pl.edu.wsiz.mapper.PostMapper;
import pl.edu.wsiz.model.Post;
import pl.edu.wsiz.model.User;
import pl.edu.wsiz.service.PostService;
import pl.edu.wsiz.service.UserService;

@RestController
@RequestMapping("user")
public class UserController extends CrudController<User, UserService> {

	PostMapper postMapper = new PostMapper();

	@Autowired
	UserService userService;

	@Autowired
	PostService postService;

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getUserPosts(@PathVariable Long userId) throws JsonProcessingException {
		List<Post> posts = postService.getUserPosts(userId);
		return postMapper.writeValueAsString(posts);
	}

	@Override
	protected UserService getService() {
		return userService;
	}

}
