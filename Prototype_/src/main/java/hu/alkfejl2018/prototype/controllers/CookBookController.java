package hu.alkfejl2018.prototype.controllers;

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
public class CookBookController {
	
	 @Autowired
	 private CookBookRepository cookBookRepository;
	 
	 @Autowired
	 private UserRepository userRepository;
	 
	 @GetMapping("/admin/getAllCookBooks")
	 public ResponseEntity<Iterable<CookBook>> getAllCookBooks() {
		 return new ResponseEntity<Iterable<CookBook>>(cookBookRepository.findAll(), HttpStatus.OK);
	 }
	 
	 @GetMapping("/admin/getAllCookBooks/getCookBookById/{cook_book_id}")
	 public ResponseEntity<Optional<CookBook>> getCookBook(@PathVariable("cook_book_id") Integer cookBookId) {
		 return new ResponseEntity<Optional<CookBook>>(cookBookRepository.findById(cookBookId), HttpStatus.OK);
	 }
	 
	 @GetMapping("/login/{user_id}/cookbooks")
	 public ResponseEntity<Iterable<CookBook>> getUserCookBooks(@PathVariable("user_id") Integer userId) {
		 return new ResponseEntity<Iterable<CookBook>>(cookBookRepository.findByUserId(userId), HttpStatus.OK);
	 }

	 @PostMapping("/login/{user_id}/cookbooks")
	 public ResponseEntity<CookBook> newCookBook(@PathVariable("user_id") int userId, @RequestBody CookBook cookBook) {
		 
		 Optional<User> user = userRepository.findById(userId);
		 user.get().getCookBooks().add(cookBook);
		 cookBook.setUser(user.get());
		
	    return ResponseEntity.ok(cookBookRepository.save(cookBook));
	}
	 
	@PutMapping("/login/{user_id}/cookbooks/{cook_book_id}")
	public ResponseEntity<CookBook> modifyCookBook(@PathVariable("cook_book_id") Integer cookBookId, @RequestBody CookBook cookBook) {
		
		Optional<CookBook> optionalCookBook = cookBookRepository.findById(cookBookId);
		if (optionalCookBook.isPresent()) {
			cookBook.setId(cookBookId);
			return ResponseEntity.ok(cookBookRepository.save(cookBook));
		}
		return ResponseEntity.notFound().build();
	 }
	
	@DeleteMapping("/login/{user_id}/cookbooks/{cook_book_id}")
	public ResponseEntity<CookBook> deleteCookBook(@PathVariable("user_id") Integer userId, @PathVariable("cook_book_id") Integer cookBookId) {
		
		Optional<CookBook> optionalCookBook = cookBookRepository.findByUserIdAndCookBookId(userId, cookBookId);
	        
		if (optionalCookBook.isPresent()) {
			cookBookRepository.deleteACookBook(userId, cookBookId);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	 }
	
	@DeleteMapping("/login/{user_id}/cookbooks")
	public ResponseEntity<CookBook> deleteCookBooks(@PathVariable("user_id") Integer userId) {
		 
		Optional<User> user = userRepository.findById(userId);
		
		if (user.isPresent()) {
			cookBookRepository.deleteCookBooksOfUser(userId);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	 }
}