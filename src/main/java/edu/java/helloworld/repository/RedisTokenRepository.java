package edu.java.helloworld.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.java.helloworld.model.RedisToken;

@Repository
public interface RedisTokenRepository extends CrudRepository<RedisToken, String> {

}
