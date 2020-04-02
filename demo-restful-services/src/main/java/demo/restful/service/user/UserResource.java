package demo.restful.service.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
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
public class UserResource {
	
	@Autowired
	private UserDAOService userService;
	

	@GetMapping(path="/users")
	public List<User> retrieveAllUser(){
		return userService.retreiveAllUsers();
	}
	
	@GetMapping(path="/users/{id}")
	public User retrieveUser(@PathVariable int id) {
		User user = userService.findById(id);
		if(user == null)
			throw new UserNotFoundException("id:-"+id);		
		/*
		 * This is for hateos implementation can be done in better way.
		 */
		/*RepresentationModel resource = new RepresentationModel();
		resource.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash(user).withSelfRel());
		resource.add(WebMvcLinkBuilder.linkTo(this.getClass()).withRel("getAllUsers"));*/
		Link link = WebMvcLinkBuilder.linkTo(this.getClass()).slash("/user/"+user.getId()).withSelfRel();
		//CollectionModel<User> result = new CollectionModel<User>(retrieveAllUser(),link);
		Link allUserLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash("/getAllUsers").withRel("all-users");
		return user.add(allUserLink);
		
	}

	@DeleteMapping(path="/user/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = userService.removeById(id);
		if(user == null)
			throw new UserNotFoundException("id:-"+id);
	}
	
	@PostMapping("/create-user")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser =  userService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
		
	}
}
