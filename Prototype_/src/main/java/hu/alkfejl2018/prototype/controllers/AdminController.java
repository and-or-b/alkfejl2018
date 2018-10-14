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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hu.alkfejl2018.prototype.entities.CookBook;
import hu.alkfejl2018.prototype.entities.Recipe;
import hu.alkfejl2018.prototype.entities.User;
import hu.alkfejl2018.prototype.repositories.CookBookRepository;
import hu.alkfejl2018.prototype.repositories.RecipeRepository;
import hu.alkfejl2018.prototype.repositories.UserRepository;

@RestController
@Secured({ "ROLE_ADMIN" })
@RequestMapping("/admin")
public class AdminController {
	
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private CookBookRepository cookBookRepository;
	 
	 @Autowired
	 private RecipeRepository recipeRepository;

	 @GetMapping("/getAllUsers")
	 public ResponseEntity<Iterable<User>> getAllUsers() {
		 return new ResponseEntity<Iterable<User>>(userRepository.findAll(), HttpStatus.OK);
	 }

	 @GetMapping("/getAllUsers/getUserById/{user_id}")
	 public ResponseEntity<Optional<User>> getUser(@PathVariable("user_id") Integer userId) {
		 
		 Optional<User> user = userRepository.findById(userId);
		 if (user.isPresent()) {
			  return ResponseEntity.ok(user);
		 }
		 return ResponseEntity.noContent().build();
	 }

	 @DeleteMapping("/deleteAllUsers")
	 public ResponseEntity<Void> deleteAllUsers() {
		 
		 if (userRepository.count() == 1) {
			 return ResponseEntity.noContent().build();
		 }
		 
		 Iterable<User> users = userRepository.findAll();
		 Iterator<User> userIterator = users.iterator();
		 
		 while(userIterator.hasNext()) {
			 User user = userIterator.next();
			 if (!user.getRole().equals(User.Role.ROLE_ADMIN)) {
				 deleteUserByAdmin(user.getId());
			 } 
		 }
		 return ResponseEntity.ok().build();
	 }

	 @DeleteMapping("/getAllUsers/deleteUserById/{user_id}")
	 public ResponseEntity<Void> deleteUserByAdmin(@PathVariable("user_id") Integer userId) {
		 
		 Optional<User> user = userRepository.findById(userId);
		 if (user.isPresent()) {
			
			List<CookBook> cookBooks = cookBookRepository.findByUserId(userId);
			for (CookBook cookBook: cookBooks) {
				deleteCookBook(cookBook.getUser().getId(), cookBook.getId());
			}

			cookBookRepository.deleteUserCookBooks(userId);
			userRepository.deleteById(userId);
			
			return ResponseEntity.ok().build();
		 }
		 return ResponseEntity.notFound().build();
	  }

	private void deleteCookBook(Integer userId, Integer cookBookId) {
			
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
		}
	}
}
