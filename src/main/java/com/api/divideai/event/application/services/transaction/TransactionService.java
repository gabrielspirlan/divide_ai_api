package com.api.divideai.event.application.services.transaction;

import com.api.divideai.event.application.dto.PageDTO;
import com.api.divideai.event.domain.dtos.transaction.TransactionRequestDTO;
import com.api.divideai.event.domain.dtos.transaction.TransactionResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {

    TransactionResponseDTO findById(String id);

    Page<TransactionResponseDTO> findAll(PageDTO pageDTO);

    Page<TransactionResponseDTO> findByGroupId(String groupId, PageDTO pageDTO);

    TransactionResponseDTO create(TransactionRequestDTO createDTO);

    TransactionResponseDTO update(String id, TransactionRequestDTO updateDTO);

    void delete(String id);
}
