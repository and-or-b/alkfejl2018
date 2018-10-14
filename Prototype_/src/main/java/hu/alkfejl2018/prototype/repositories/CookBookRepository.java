package hu.alkfejl2018.prototype.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import hu.alkfejl2018.prototype.entities.CookBook;
import hu.alkfejl2018.prototype.entities.Recipe;

@Repository
public interface CookBookRepository extends CrudRepository<CookBook, Integer> {
	
	List<CookBook> findByUserId(Integer userId);
	
	@Query("select cookBook FROM CookBook cookBook where cookBook.id = :cookBookId and cookBook.user.id = :userId")
	Optional<CookBook> findByUserIdAndCookBookId(Integer userId, Integer cookBookId);

	@Query("select cookBook.recipes FROM CookBook cookBook where cookBook.id = :cookBookId and cookBook.user.id = :userId")
	Iterable<Recipe> findAllRecipesFromCookBook(Integer userId, Integer cookBookId);

	@Transactional
	@Modifying
	@Query("delete FROM CookBook cookBook where cookBook.user.id = :userId")
	void deleteUserCookBooks(Integer userId);

	@Transactional
	@Modifying
	@Query("delete FROM CookBook cookBook where cookBook.id = :cookBookId and cookBook.user.id = :userId")
	void deleteUserCookBook(Integer userId, Integer cookBookId);
}