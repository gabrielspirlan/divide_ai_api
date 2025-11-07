package com.api.divideai.event.web.controller;

import com.api.divideai.event.application.dto.PageDTO;
import com.api.divideai.event.application.services.transaction.TransactionService;
import com.api.divideai.event.domain.dtos.transaction.TransactionRequestDTO;
import com.api.divideai.event.domain.dtos.transaction.TransactionResponseDTO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Page<TransactionResponseDTO>> getMany(@ParameterObject PageDTO pageDTO) {
        return new ResponseEntity<>(
                transactionService.findAll(pageDTO),
                HttpStatus.OK
        );
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<Page<TransactionResponseDTO>> getByGroupId(
            @PathVariable String groupId,
            @ParameterObject PageDTO pageDTO) {
        return new ResponseEntity<>(
                transactionService.findByGroupId(groupId, pageDTO),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getOne(@PathVariable String id) {
        return new ResponseEntity<>(
                transactionService.findById(id),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> create(@RequestBody TransactionRequestDTO createDTO) {
        return new ResponseEntity<>(
                transactionService.create(createDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> update(@PathVariable String id, @RequestBody TransactionRequestDTO updateDTO) {
        return new ResponseEntity<>(
                transactionService.update(id, updateDTO),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        transactionService.delete(id);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }
}

