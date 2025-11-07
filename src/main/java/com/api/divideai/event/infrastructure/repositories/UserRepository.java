package com.api.divideai.event.infrastructure.repositories;

import com.api.divideai.event.domain.collections.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
}
