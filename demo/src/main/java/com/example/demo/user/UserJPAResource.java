package com.example.demo.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
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
	/*
	 * private static final String Resource = null; private static final String
	 * ControllerLinkBuilder = null;
	 * 
	 * @Autowired private UserDaoService service;
	 */

	@Autowired
	private UserRepository userRepository;
	// retriveallusers;

	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/jpa/users/{id}")
	public EntityModel<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);

		if (!user.isPresent())
			throw new UsernotfoundException("id-" + id);
		// HATEOS to add links in response
		EntityModel<User> resource = EntityModel.of(user.get());
		Link link = WebMvcLinkBuilder.linkTo(methodOn(this.getClass()).retrieveAllUsers()).withRel("all-users");
		resource.add(link);
		return resource;

	}

	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
		/*
		 * if(user==null) throw new UsernotfoundException("id-"+ id);
		 */
	}

	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {

		User savedUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
				.buildAndExpand(savedUser.getId()).toUri();

		return ResponseEntity.created(location).build();
	}
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveAllUsers(@PathVariable int id) {
		Optional<User> userOptional = userRepository.findById(id);
		
		if (!userOptional.isPresent()) {
			throw new UsernotfoundException("id-" + id);
	}
	return userOptional.get().getPosts();	
			}
}
