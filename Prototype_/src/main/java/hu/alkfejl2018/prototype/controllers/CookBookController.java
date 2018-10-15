package hu.alkfejl2018.prototype.controllers;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.alkfejl2018.prototype.entities.CookBook;
import hu.alkfejl2018.prototype.entities.Recipe;
import hu.alkfejl2018.prototype.entities.User;
import hu.alkfejl2018.prototype.repositories.CookBookRepository;
import hu.alkfejl2018.prototype.repositories.RecipeRepository;
import hu.alkfejl2018.prototype.repositories.UserRepository;
import hu.alkfejl2018.prototype.security.AuthenticatedUser;

@RestController
@Secured({ "ROLE_USER" })
@RequestMapping("/user/{user_id}/cookbooks")
public class CookBookController {
	
	 @Autowired
	 private CookBookRepository cookBookRepository;
	 
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private RecipeRepository recipeRepository;
	 
	 @Autowired 
	 private AuthenticatedUser authenticatedUser;

	 @GetMapping("")
	 public ResponseEntity<Iterable<CookBook>> getUserCookBooks(@PathVariable("user_id") Integer userId) {
		 
		 if (!authenticatedUser.getUser().getId().equals(userId)) {
			 return ResponseEntity.badRequest().build();
		 }
		 return new ResponseEntity<Iterable<CookBook>>(cookBookRepository.findByUserId(userId), HttpStatus.OK);
	 }

	 @PostMapping("")
	 public ResponseEntity<CookBook> newCookBook(@PathVariable("user_id") Integer userId, @RequestBody CookBook cookBook) {
		 
		 if (!authenticatedUser.getUser().getId().equals(userId)) {
			 return ResponseEntity.badRequest().build();
		 }
		 
		 Optional<User> user = userRepository.findById(userId);
		 if (user.isPresent()) {
			 
			 user.get().getCookBooks().add(cookBook);
			 cookBook.setUser(user.get());
			 
			 return ResponseEntity.ok(cookBookRepository.save(cookBook));
		 }
		 return ResponseEntity.notFound().build();
	}

	@PutMapping("/{cook_book_id}")
	public ResponseEntity<CookBook> renameCookBook(@PathVariable("user_id") Integer userId, @PathVariable("cook_book_id") Integer cookBookId, 
			@RequestBody CookBook cookBook) {
		
		if (!authenticatedUser.getUser().getId().equals(userId)) {
			 return ResponseEntity.badRequest().build();
		}
		
		Optional<CookBook> optionalCookBook = cookBookRepository.findByUserIdAndCookBookId(userId, cookBookId);
		if (optionalCookBook.isPresent() && cookBook.getTitle() != null && cookBook.getTitle() != "") {
			
			optionalCookBook.get().setTitle(cookBook.getTitle());
			
			return ResponseEntity.ok(cookBookRepository.save(optionalCookBook.get()));
		}
		return ResponseEntity.notFound().build();
	 }

	@DeleteMapping("/{cook_book_id}")
	public ResponseEntity<CookBook> deleteCookBook(@PathVariable("user_id") Integer userId, @PathVariable("cook_book_id") Integer cookBookId) {
		
		if (!authenticatedUser.getUser().getId().equals(userId)) {
			 return ResponseEntity.badRequest().build();
		}
		
		Optional<CookBook> optionalCookBook = cookBookRepository.findByUserIdAndCookBookId(userId, cookBookId);
		if (optionalCookBook.isPresent()) {
			
			Iterable<Recipe> recipes = cookBookRepository.findAllRecipesFromCookBook(userId, cookBookId);
			Iterator<Recipe> recipeIterator = recipes.iterator();
			
			while(recipeIterator.hasNext()) {
				
				Recipe recipe = recipeIterator.next();
				
				optionalCookBook.get().getRecipes().remove(recipe);
				recipe.getCookBooks().remove(optionalCookBook.get());
				
				recipeRepository.save(recipe);
				
				if (recipeRepository.findById(recipe.getId()).get().getCookBooks().size() == 0) {
					recipeRepository.deleteById(recipe.getId());
				}
			}
			cookBookRepository.deleteUserCookBook(userId, cookBookId);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	 }

	@DeleteMapping("")
	public ResponseEntity<CookBook> deleteCookBooks(@PathVariable("user_id") Integer userId) {
		
		if (!authenticatedUser.getUser().getId().equals(userId)) {
			 return ResponseEntity.badRequest().build();
		}
		
		Optional<User> user = userRepository.findById(userId);
		if (user.isPresent()) {
			
			List<CookBook> cookBooks = cookBookRepository.findByUserId(userId);
			
			for (CookBook cookBook: cookBooks) {
				deleteCookBook(cookBook.getUser().getId(), cookBook.getId());
			}
			cookBookRepository.deleteUserCookBooks(userId);
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	 }
}