package pl.edu.wsiz.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import pl.edu.wsiz.core.BaseEntity;

@Entity
@Table(name = "post")
@JsonFilter("postFilter")
@JsonPropertyOrder(alphabetic = true)
public class Post extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	@NotNull
	private User user;

	@Column(name = "image_url")
	private String imageURL;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;

	@Column(name = "create_date", nullable = false)
	@NotNull
	private LocalDateTime createDate;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "likes", joinColumns = { @JoinColumn(name = "post_id") }, inverseJoinColumns = {
			@JoinColumn(name = "user_id") })
	Set<User> usersWhoLike;

	public Long getUserId() {
		return user.getId();
	}

	public Integer getLikesCount() {
		return usersWhoLike.size();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public Set<User> getUsersWhoLike() {
		return usersWhoLike;
	}

	public void setUsersWhoLike(Set<User> usersWhoLike) {
		this.usersWhoLike = usersWhoLike;
	}

}