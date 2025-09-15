package com.api.divideai.health;

import com.api.divideai.event.infrastructure.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Verificar conexão com o banco
            long eventCount = eventRepository.count();
            
            response.put("status", "UP");
            response.put("timestamp", LocalDateTime.now());
            response.put("application", "DivideAI API");
            response.put("version", "1.0.0");
            
            Map<String, Object> database = new HashMap<>();
            database.put("status", "UP");
            database.put("type", "MongoDB");
            database.put("totalEvents", eventCount);
            
            response.put("database", database);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("status", "DOWN");
            response.put("timestamp", LocalDateTime.now());
            response.put("application", "DivideAI API");
            response.put("version", "1.0.0");
            
            Map<String, Object> database = new HashMap<>();
            database.put("status", "DOWN");
            database.put("type", "MongoDB");
            database.put("error", e.getMessage());
            
            response.put("database", database);
            
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
        }
    }
}
