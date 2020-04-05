package demo.restful.service.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserJPAResource {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;


	@GetMapping(path="/jpa/users")
	public List<User> retrieveAllUser(){

		return userRepository.findAll();
	}

	@GetMapping(path="/jpa/users/{id}")
	public User retrieveUser(@PathVariable int id) {

		Optional<User> user =userRepository.findById(id);
		if(!user.isPresent())
			throw new UserNotFoundException("id:-"+id);		
		/*
		 * This is for hateos implementation can be done in better way.
		 */
		/*RepresentationModel resource = new RepresentationModel();
		resource.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash(user).withSelfRel());
		resource.add(WebMvcLinkBuilder.linkTo(this.getClass()).withRel("getAllUsers"));*/
		Link link = WebMvcLinkBuilder.linkTo(this.getClass()).slash("/user/"+user.get().getId()).withSelfRel();
		//CollectionModel<User> result = new CollectionModel<User>(retrieveAllUser(),link);
		Link allUserLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash("/getAllUsers").withRel("all-users");
		return user.get().add(allUserLink) ;

	}

	@DeleteMapping(path="/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);

	}

	@PostMapping("/jpa/users/create-user")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser =  userRepository.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedUser.getId()).toUri();
		
		return ResponseEntity.created(location).build();

	}

	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveAllPosts(@PathVariable int id){
		Optional<User> userOptional = userRepository.findById(id);
		
		if(!userOptional.isPresent())
			throw new UserNotFoundException("id:-"+id);	
		
		return userOptional.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Post> createPost(@PathVariable int id, @RequestBody Post post) {
		
		Optional<User> userOptional = userRepository.findById(id);

		if(!userOptional.isPresent())
			throw new UserNotFoundException("id:-"+id);	
		
		User user =  userOptional.get();
		
		post.setUser(user);
		
		postRepository.save(post);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(post.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
}
