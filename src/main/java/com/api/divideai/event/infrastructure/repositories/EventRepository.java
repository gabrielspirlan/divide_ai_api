package com.api.divideai.event.infrastructure.repositories;

import com.api.divideai.event.domain.collections.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {
}
