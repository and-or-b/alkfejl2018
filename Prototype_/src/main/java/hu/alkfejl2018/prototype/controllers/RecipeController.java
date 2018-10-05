package hu.alkfejl2018.prototype.controllers;

import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import hu.alkfejl2018.prototype.entities.User;
import hu.alkfejl2018.prototype.repositories.CookBookRepository;
import hu.alkfejl2018.prototype.repositories.RecipeRepository;

@RestController
@RequestMapping("")
public class RecipeController {
	
	 @Autowired
	 private RecipeRepository recipeRepository;
	 
	 @Autowired
	 private CookBookRepository cookBookRepository;
	 
	 @GetMapping("/admin/getAllRecipes")
	 public ResponseEntity<Iterable<Recipe>> getAllRecipes() {
		 return new ResponseEntity<Iterable<Recipe>>(recipeRepository.findAll(), HttpStatus.OK);
	 }
	 
	 @GetMapping("/admin/getAllRecipes/getRecipeById/{recipe_id}")
	 public ResponseEntity<Optional<Recipe>> getRecipe(@PathVariable("recipe_id") Integer recipeId) {
		 return new ResponseEntity<Optional<Recipe>>(recipeRepository.findById(recipeId), HttpStatus.OK);
	 }
	 
	 @GetMapping("/login/{user_id}/cookbooks/{cook_book_id}/recipes")
	 public ResponseEntity<Iterable<Recipe>> getUserRecipesFromACookBook(@PathVariable("user_id") Integer userId,
			 @PathVariable("cook_book_id") Integer cookBookId) {
		
		 Optional<CookBook> cookBook = cookBookRepository.findByUserIdAndCookBookId(userId, cookBookId);

		 if (cookBook.isPresent()) {
			 return new ResponseEntity<Iterable<Recipe>>(cookBookRepository.findAllRecipes(userId, cookBookId), HttpStatus.OK);
	     }
		 return ResponseEntity.notFound().build();
	 }

	 @PostMapping("/login/{user_id}/cookbooks/{cook_book_id}/recipes")
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
	 @DeleteMapping("/login/{user_id}/cookbooks/{cook_book_id}/recipes")
	 public ResponseEntity<Recipe> deleteRecipes(@PathVariable("user_id") Integer userId, @PathVariable("cook_book_id") int cookBookId) {
		 
		 Optional<CookBook> cookBook = cookBookRepository.findByUserIdAndCookBookId(userId, cookBookId);
		
		 if (cookBook.isPresent()) {
			 Iterable<Recipe> recipes = cookBookRepository.findAllRecipes(userId, cookBookId);
			 cookBook.get().getRecipes().removeAll(cookBook.get().getRecipes());
			
			Iterator<Recipe> iterator = recipes.iterator();
			
			while(iterator.hasNext()) {
				Recipe recipe = iterator.next();
				recipeRepository.deleteById(recipe.getId());
			}
		 	return ResponseEntity.ok().build();
		 }
		 return ResponseEntity.notFound().build();
	  }
	 
	 @Transactional
	 @Modifying
	 @DeleteMapping("/login/{user_id}/cookbooks/{cook_book_id}/recipes/{recipe_id}")
	 public ResponseEntity<Recipe> deleteRecipe(@PathVariable("user_id") Integer userId, @PathVariable("cook_book_id") Integer cookBookId, 
			 @PathVariable("recipe_id") Integer recipeId) {
		 
		 Optional<CookBook> cookBook = cookBookRepository.findByUserIdAndCookBookId(userId, cookBookId);
		
		 if (cookBook.isPresent()) {
			 
			 Optional<Recipe> recipes = recipeRepository.findById(recipeId);
			 cookBook.get().getRecipes().remove(recipes.get());
			 recipeRepository.deleteById(recipeId);
		
		 	return ResponseEntity.ok().build();
		 }
		 return ResponseEntity.notFound().build();
	  }
	 
	 @PutMapping("/login/{user_id}/cookbooks/{cook_book_id}/recipes/{recipe_id}")
	 public ResponseEntity<Recipe> modifyRecipe(@PathVariable("recipe_id") Integer recipeId, @RequestBody Recipe recipe) {
			
		 Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
		 
		 if (optionalRecipe.isPresent()) {
			 recipe.setId(recipeId);
			 
			 return ResponseEntity.ok(recipeRepository.save(recipe));
		 }
		 return ResponseEntity.notFound().build();
	 }
}
