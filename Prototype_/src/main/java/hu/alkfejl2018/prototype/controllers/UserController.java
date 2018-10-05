package hu.alkfejl2018.prototype.controllers;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.alkfejl2018.prototype.entities.CookBook;
import hu.alkfejl2018.prototype.entities.User;
import hu.alkfejl2018.prototype.repositories.CookBookRepository;
import hu.alkfejl2018.prototype.repositories.UserRepository;

@RestController
@RequestMapping("")
public class UserController {
	
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private CookBookRepository cookBookRepository;
	 
	 @GetMapping("/admin/getAllUsers")
	 public ResponseEntity<Iterable<User>> getAllUsers() {
		 return new ResponseEntity<Iterable<User>>(userRepository.findAll(), HttpStatus.OK);
	 }
	 
	 @GetMapping("/admin/getAllUsers/getUserById/{user_id}")
	 public ResponseEntity<Optional<User>> getUser(@PathVariable("user_id") Integer userId) {
		 return new ResponseEntity<Optional<User>>(userRepository.findById(userId), HttpStatus.OK);
	 }
	 
	 @DeleteMapping("/admin/deleteAllUsers")
	 public void deleteAllUsers() {
		 
		 Iterable<User> users = userRepository.findAll();
		 Iterator<User> iterator = users.iterator();
		 while(iterator.hasNext()) {
			 
			 User user = iterator.next();
			 int id = user.getId();
			 List<CookBook> cookBooks = cookBookRepository.findByUserId(id);
			 
			 if (cookBooks.size() != 0) {
				 cookBookRepository.deleteCookBooksOfUser(id);
			 }
			 
			 userRepository.deleteById(id);
		 }
	 }
	 
	 @DeleteMapping("/admin/getAllUsers/deleteUserById/{user_id}")
	 public ResponseEntity<Void> deleteUserByAdmin(@PathVariable("user_id") Integer userId) {
		 
		 Optional<User> user = userRepository.findById(userId);
		 
		 if (user.isPresent()) {
			
			cookBookRepository.deleteCookBooksOfUser(userId);
			userRepository.deleteById(userId);
			
		 	return ResponseEntity.ok().build();
		 }
		 return ResponseEntity.notFound().build();
	  }
	 
	 
	 @PostMapping("/registration")
	 public ResponseEntity<User> newUser(@RequestBody User user) {
		 User newUser = userRepository.save(user);
		 return ResponseEntity.ok(newUser);
	}
	 
	@PutMapping("/login/{user_id}")
	public ResponseEntity<User> modifyUser(@PathVariable("user_id") Integer userId, @RequestBody User user) {
		
		Optional<User> optionalUser = userRepository.findById(userId);
		
		if (optionalUser.isPresent()) {
			user.setId(userId);
			return ResponseEntity.ok(userRepository.save(user));
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/login/{user_id}")
	 public ResponseEntity<User> deleteUser(@PathVariable("user_id") Integer userId) {
		 
		 Optional<User> user = userRepository.findById(userId);

		 if (user.isPresent()) {
			
			cookBookRepository.deleteCookBooksOfUser(userId);
			userRepository.deleteById(userId);
			
		 	return ResponseEntity.ok().build();
		 }
		 return ResponseEntity.notFound().build();
	  }
}
