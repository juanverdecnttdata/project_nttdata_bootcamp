package com.nttdata.repository;

import com.nttdata.entity.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Class repository of the User.
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, Long> {
}
