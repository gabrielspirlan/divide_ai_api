package com.api.divideai.event.infrastructure.repositories;

import com.api.divideai.event.domain.collections.Event;
import com.api.divideai.event.domain.enums.EventType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends MongoRepository<Event, String> {

    // Buscar eventos por tipo
    List<Event> findByEventType(EventType eventType);

    // Contar eventos por tipo
    Long countByEventType(EventType eventType);

    // Buscar últimos eventos por tipo (ordenados por data de criação decrescente)
    List<Event> findByEventTypeOrderByCreatedAtDesc(EventType eventType, Pageable pageable);

    // Calcular média de loading time usando agregação
    @Query(value = "{ 'eventType': ?0 }", fields = "{ 'loading': 1 }")
    List<Event> findLoadingTimesByEventType(EventType eventType);

    // Buscar todos os eventos de loading para calcular média
    @Query(value = "{ 'loading': { $exists: true, $ne: null } }", fields = "{ 'loading': 1 }")
    List<Event> findAllWithLoadingTime();

    // Buscar o item que demorou mais tempo para carregar
    @Query(value = "{ 'loading': { $exists: true, $ne: null } }")
    List<Event> findAllWithLoadingTimeOrderByLoadingDesc();

    // Buscar elementos mais clicados (agrupados por elementId)
    @Query(value = "{ 'eventType': 'CLICK' }")
    List<Event> findAllClickEvents();

    // Buscar páginas mais acessadas (agrupados por page)
    @Query(value = "{ 'eventType': 'PAGE_VIEW' }")
    List<Event> findAllPageViewEvents();
}
