package hu.alkfejl2018.prototype.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.alkfejl2018.prototype.entities.User;
import hu.alkfejl2018.prototype.repositories.UserRepository;

@RestController
@RequestMapping("")
public class LoginAndRegisterController {
	
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private BCryptPasswordEncoder passwordEncoder;
	 
	 @PostMapping("/register")
	 public ResponseEntity<User> register(@RequestBody User user) {
		 
	    Optional<User> oUser = userRepository.findByEmail(user.getEmail());
	    if (oUser.isPresent()) {
	        return ResponseEntity.badRequest().build();
	    }
	     
	    user.setPassword(passwordEncoder.encode(user.getPassword()));
	    user.setRole(User.Role.ROLE_USER);
	    return ResponseEntity.ok(userRepository.save(user));
	 }

	 @PostMapping("/login")
	 public ResponseEntity<User> login(@RequestBody User user) {
		 return ResponseEntity.ok().build();
	 }
}
