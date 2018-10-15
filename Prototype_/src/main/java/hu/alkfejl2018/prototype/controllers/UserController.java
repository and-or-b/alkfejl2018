package hu.alkfejl2018.prototype.controllers;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/user")
public class UserController {
	
	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private CookBookRepository cookBookRepository;
	
	 @Autowired
	 private RecipeRepository recipeRepository;

	 @Autowired
	 private BCryptPasswordEncoder passwordEncoder;
	 
	 @Autowired 
	 private AuthenticatedUser authenticatedUser;
	 
	 @PutMapping("/{user_id}")
	 public ResponseEntity<User> modifyUser(@PathVariable("user_id") Integer userId, @RequestBody User user) {
		 
		 if (!authenticatedUser.getUser().getId().equals(userId)) {
			 return ResponseEntity.badRequest().build();
		 }
		 
		 Optional<User> optionalUser = userRepository.findById(userId);
		 if (optionalUser.isPresent()) {
			 
			 user.setId(userId);
			 user.setPassword(passwordEncoder.encode(user.getPassword()));
			 user.setRole(optionalUser.get().getRole());

			 return ResponseEntity.ok(userRepository.save(user));
		 }
		 return ResponseEntity.notFound().build();
	 }

	 @DeleteMapping("/{user_id}")
	 public ResponseEntity<User> deleteUser(@PathVariable("user_id") Integer userId) {
		 
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
