package com.api.divideai.event.infrastructure.repositories;

import com.api.divideai.event.domain.collections.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

    @Aggregation(pipeline = {
            "{ '$match': { 'group': ?0 } }",
            "{ '$group': { '_id': null, 'total': { '$sum': '$value' } } }"
    })
    Double sumValueByGroup(String groupId);

    // Buscar transações por ID do grupo
    Page<Transaction> findByGroup(String groupId, Pageable pageable);
}
