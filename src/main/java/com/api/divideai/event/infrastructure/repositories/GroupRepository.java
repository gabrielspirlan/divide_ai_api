package com.api.divideai.event.infrastructure.repositories;

import com.api.divideai.event.domain.collections.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> {

    // Buscar grupos onde o usuário está na lista de participantes
    Page<Group> findByParticipantsContaining(String userId, Pageable pageable);
}
