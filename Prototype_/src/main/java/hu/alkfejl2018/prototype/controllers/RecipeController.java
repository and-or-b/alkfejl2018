package hu.alkfejl2018.prototype.controllers;

import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
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
import hu.alkfejl2018.prototype.repositories.CookBookRepository;
import hu.alkfejl2018.prototype.repositories.RecipeRepository;

@RestController
@Secured({ "ROLE_USER" })
@RequestMapping("/user/{user_id}/cookbooks/{cook_book_id}/recipes")
public class RecipeController {	
	
	@Autowired
	private RecipeRepository recipeRepository;
	 
	@Autowired
	private CookBookRepository cookBookRepository;
	 
	@GetMapping("")
	public ResponseEntity<Iterable<Recipe>> getUserRecipesFromACookBook(@PathVariable("user_id") Integer userId,
			@PathVariable("cook_book_id") Integer cookBookId) {
		
		Optional<CookBook> cookBook = cookBookRepository.findByUserIdAndCookBookId(userId, cookBookId);
		if (cookBook.isPresent()) {
			return new ResponseEntity<Iterable<Recipe>>(cookBookRepository.findAllRecipesFromCookBook(userId, cookBookId), HttpStatus.OK);
	    }
		return ResponseEntity.notFound().build();
	}
	 
	@PostMapping("")
	public ResponseEntity<Recipe> newRecipe(@PathVariable("user_id") Integer userId, 
			@PathVariable("cook_book_id") Integer cookBookId, @RequestBody Recipe recipe) {
		 
		Optional<CookBook> cookBook = cookBookRepository.findByUserIdAndCookBookId(userId, cookBookId);
		if (cookBook.isPresent()) {

			recipe.getCookBooks().add(cookBook.get());
			Recipe savedRecipe = recipeRepository.save(recipe);
			 
			cookBook.get().getRecipes().add(savedRecipe);
			cookBookRepository.save(cookBook.get());
			 
			return ResponseEntity.ok().build();
	    }
		return ResponseEntity.notFound().build();
	}
	 
	@Transactional
	@Modifying
	@DeleteMapping("")
	public ResponseEntity<Recipe> deleteRecipes(@PathVariable("user_id") Integer userId, @PathVariable("cook_book_id") int cookBookId) {
		 
		Optional<CookBook> cookBook = cookBookRepository.findByUserIdAndCookBookId(userId, cookBookId);
		if (cookBook.isPresent()) {
			
			Iterable<Recipe> recipes = cookBookRepository.findAllRecipesFromCookBook(userId, cookBookId);
			Iterator<Recipe> recipeIterator = recipes.iterator();
			while(recipeIterator.hasNext()) {
				deleteRecipe(userId, cookBookId, recipeIterator.next().getId());

			}
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	 
	@Transactional
	@Modifying
	@DeleteMapping("/{recipe_id}")
	public ResponseEntity<Recipe> deleteRecipe(@PathVariable("user_id") Integer userId, @PathVariable("cook_book_id") Integer cookBookId, 
			@PathVariable("recipe_id") Integer recipeId) {
		 
		Optional<CookBook> cookBook = cookBookRepository.findByUserIdAndCookBookId(userId, cookBookId);
		Optional<Recipe> recipe = recipeRepository.findById(recipeId);
		if (cookBook.isPresent() && recipe.isPresent()) {
			
			cookBook.get().getRecipes().remove(recipe.get());
			recipe.get().getCookBooks().remove(cookBook.get());

			if (recipeRepository.findById(recipeId).get().getCookBooks().size() == 0) {
				recipeRepository.deleteById(recipeId);
			}
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}
	 
	@PutMapping("/{recipe_id}")
	public ResponseEntity<Recipe> modifyRecipe(@PathVariable("recipe_id") Integer recipeId, @RequestBody Recipe recipe) {
			
		Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
		if (optionalRecipe.isPresent()) {
			recipe.setId(recipeId);
			 
			return ResponseEntity.ok(recipeRepository.save(recipe));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/{recipe_id}/addToAnotherCookBook/{other_cook_book_id}")
	public ResponseEntity<Recipe> addToAnotherCookBook(@PathVariable("user_id") Integer userId, @PathVariable("recipe_id") Integer recipeId, 
			@PathVariable("other_cook_book_id") Integer otherCookBookId) {
			
		Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
		Optional<CookBook> optionalCookBook = cookBookRepository.findByUserIdAndCookBookId(userId, otherCookBookId);
		if (optionalRecipe.isPresent() && optionalCookBook.isPresent()) {
			
			Recipe recipe = optionalRecipe.get();
			CookBook cookBook = optionalCookBook.get();
			if (!cookBook.getRecipes().contains(recipe)) {
				
				recipe.getCookBooks().add(cookBook);
				recipeRepository.save(recipe);
				 
				cookBook.getRecipes().add(recipe);
				cookBookRepository.save(cookBook);
				
				return ResponseEntity.ok().build();
			}
		}
		return ResponseEntity.badRequest().build();
	}
}
