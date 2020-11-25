package pl.edu.wsiz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import pl.edu.wsiz.core.CrudController;
import pl.edu.wsiz.mapper.PostMapper;
import pl.edu.wsiz.model.Post;
import pl.edu.wsiz.service.PostService;

@RestController
@RequestMapping("post")
public class PostController extends CrudController<Post, PostService> {

	PostMapper postMapper = new PostMapper();

	@Autowired
	PostService postService;

	@Override
	protected PostService getService() {
		return postService;
	}

	@RequestMapping(value = "/get-by-user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<String> getByUserId(@PathVariable Long id) throws JsonProcessingException {
		List<Post> posts = getService().listByUserId(id);
		return ResponseEntity.ok().body(postMapper.writeValueAsString(posts));
	}
}
