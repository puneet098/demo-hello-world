package demo.restful.service.controller;

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

import demo.restful.service.UserService;
import demo.restful.service.data.In28User;

@SuppressWarnings("deprecation")
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	

	@GetMapping(path="/getAllUsers")
	public List<In28User> retrieveAllUser(){
		return userService.retreiveAllUsers();
	}
	
	@GetMapping(path="/user/{id}")
	public In28User retrieveUser(@PathVariable int id) {
		In28User user = userService.findById(id);
		if(user == null)
			throw new UserNotFoundException("id:-"+id);		
		/*
		 * This is for hateos implementation can be done in better way.
		 */
		/*RepresentationModel resource = new RepresentationModel();
		resource.add(WebMvcLinkBuilder.linkTo(this.getClass()).slash(user).withSelfRel());
		resource.add(WebMvcLinkBuilder.linkTo(this.getClass()).withRel("getAllUsers"));*/
		Link link = WebMvcLinkBuilder.linkTo(this.getClass()).slash("/user/"+user.getId()).withSelfRel();
		//CollectionModel<In28User> result = new CollectionModel<In28User>(retrieveAllUser(),link);
		Link allUserLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash("/getAllUsers").withRel("all-users");
		return user.add(link).add(allUserLink);
	}

	@DeleteMapping(path="/user/{id}")
	public void deleteUser(@PathVariable int id) {
		In28User user = userService.removeById(id);
		if(user == null)
			throw new UserNotFoundException("id:-"+id);
	}
	
	@PostMapping("/create-user")
	public ResponseEntity<In28User> createUser(@Valid @RequestBody In28User user) {
		In28User savedUser =  userService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
		
	}
}
