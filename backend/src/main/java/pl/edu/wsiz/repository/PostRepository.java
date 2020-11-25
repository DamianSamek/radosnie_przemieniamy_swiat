package pl.edu.wsiz.repository;

import java.util.List;

import pl.edu.wsiz.core.CoreRepository;
import pl.edu.wsiz.model.Post;

public interface PostRepository extends CoreRepository<Post, Long> {

	List<Post> findAllByUserIdOrderByCreateDateDesc(Long userId);
}
