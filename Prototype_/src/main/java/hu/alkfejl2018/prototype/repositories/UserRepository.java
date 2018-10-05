package hu.alkfejl2018.prototype.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import hu.alkfejl2018.prototype.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {}
