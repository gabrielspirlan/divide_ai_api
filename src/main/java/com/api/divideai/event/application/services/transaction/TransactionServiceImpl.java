package com.api.divideai.event.application.services.transaction;

import com.api.divideai.event.application.dto.PageDTO;
import com.api.divideai.event.domain.collections.Transaction;
import com.api.divideai.event.domain.dtos.transaction.TransactionRequestDTO;
import com.api.divideai.event.domain.dtos.transaction.TransactionResponseDTO;
import com.api.divideai.event.domain.dtos.transaction.UserTransactionTotalsResponseDTO;
import com.api.divideai.event.infrastructure.repositories.TransactionRepository;
import com.api.divideai.event.infrastructure.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {


    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public TransactionResponseDTO findById(String id) {

       Transaction transaction = transactionRepository.findById(id)
               .orElseThrow(() -> new RuntimeException("Não existe transação com o id: " + id));


        return mapTransactionToDTO(transaction);
    }

    @Override
    public Page<TransactionResponseDTO> findAll(PageDTO pageDTO) {


        pageDTO.sortByDate();

        Page<Transaction> transactions = transactionRepository.findAll(pageDTO.mapPage());

        List<TransactionResponseDTO> dtoTransactions = transactions.getContent().stream()
                .map(this::mapTransactionToDTO)
                .toList();


        return new PageImpl<TransactionResponseDTO>(dtoTransactions, pageDTO.mapPage(), transactions.getTotalElements());
    }

    @Override
    public Page<TransactionResponseDTO> findByGroupId(String groupId, PageDTO pageDTO) {

        pageDTO.sortByDate();

        Page<Transaction> transactions = transactionRepository.findByGroup(groupId, pageDTO.mapPage());

        List<TransactionResponseDTO> dtoTransactions = transactions.getContent().stream()
                .map(this::mapTransactionToDTO)
                .toList();

        return new PageImpl<TransactionResponseDTO>(dtoTransactions, pageDTO.mapPage(), transactions.getTotalElements());
    }

    @Override
    public UserTransactionTotalsResponseDTO getUserTransactionTotals(String userId) {
        // Verificar se o usuário existe
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuário com o id: " + userId + " não existe"));

        // Buscar todas as transações onde o usuário participa
        Page<Transaction> transactionsPage = transactionRepository.findByParticipantsContaining(userId, Pageable.unpaged());
        List<Transaction> transactions = transactionsPage.getContent();

        double totalExpenses = 0.0;
        double individualExpenses = 0.0;
        double sharedExpenses = 0.0;

        for (Transaction transaction : transactions) {
            double value = transaction.getValue() != null ? transaction.getValue() : 0.0;

            // Se tem mais de um participante, é compartilhado
            if (transaction.getParticipants() != null && transaction.getParticipants().size() > 1) {
                // Calcular o valor que o usuário pagou (valor dividido pelo número de participantes)
                double valuePerPerson = value / transaction.getParticipants().size();
                sharedExpenses += valuePerPerson;
                totalExpenses += valuePerPerson;
            } else {
                // Gasto individual
                individualExpenses += value;
                totalExpenses += value;
            }
        }

        // Arredondar valores para 2 casas decimais
        totalExpenses = roundToTwoDecimals(totalExpenses);
        individualExpenses = roundToTwoDecimals(individualExpenses);
        sharedExpenses = roundToTwoDecimals(sharedExpenses);

        return new UserTransactionTotalsResponseDTO(totalExpenses, individualExpenses, sharedExpenses);
    }

    @Override
    public TransactionResponseDTO create(TransactionRequestDTO createDTO) {

        Transaction transactionToSave = mapper.map(createDTO, Transaction.class);

        return mapper.map(transactionRepository.save(transactionToSave), TransactionResponseDTO.class);
    }

    @Override
    public TransactionResponseDTO update(String id, TransactionRequestDTO updateDTO) {

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não existe transação com o id: " + id));

        if (updateDTO == null) {
            throw new RuntimeException("Dados da transação não enviados");
        }

        if (updateDTO.getDescription() != null && !updateDTO.getDescription().isEmpty()) {
            transaction.setDescription(updateDTO.getDescription());
        }

        if (updateDTO.getValue() != null) {
            transaction.setValue(updateDTO.getValue());
        }

        if (updateDTO.getParticipants() != null && !updateDTO.getParticipants().isEmpty()) {
            transaction.setParticipants(updateDTO.getParticipants());
        }

        if (updateDTO.getGroup() != null && !updateDTO.getGroup().isEmpty()) {
            transaction.setGroup(updateDTO.getGroup());
        }

        if (updateDTO.getDate() != null) {
            transaction.setDate(updateDTO.getDate());
        }

        return mapper.map(transactionRepository.save(transaction), TransactionResponseDTO.class);
    }

    @Override
    public void delete(String id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Não existe transação com o id: " + id));
        transactionRepository.delete(transaction);
    }

    private TransactionResponseDTO mapTransactionToDTO(Transaction transaction) {
        TransactionResponseDTO dto = mapper.map(transaction, TransactionResponseDTO.class);

        // Se a transação tem mais de um participante, é compartilhada
        if (transaction.getParticipants() != null && transaction.getParticipants().size() > 1) {
            Double valuePerPerson = calculateValuePerPerson(transaction.getValue(), transaction.getParticipants().size());
            dto.setValuePerPerson(valuePerPerson);
        }

        // Buscar nomes dos participantes
        if (transaction.getParticipants() != null && !transaction.getParticipants().isEmpty()) {
            List<String> participantNames = getParticipantNames(transaction.getParticipants());
            dto.setParticipantNames(participantNames);
        }

        return dto;
    }

    private List<String> getParticipantNames(List<String> participantIds) {
        return participantIds.stream()
                .map(id -> userRepository.findById(id)
                        .map(user -> user.getName())
                        .orElse("Usuário não encontrado"))
                .toList();
    }

    private Double calculateValuePerPerson(Double totalValue, int numberOfParticipants) {
        if (totalValue == null || numberOfParticipants == 0) {
            return 0.0;
        }

        BigDecimal value = BigDecimal.valueOf(totalValue);
        BigDecimal participants = BigDecimal.valueOf(numberOfParticipants);

        return value.divide(participants, 2, RoundingMode.HALF_UP).doubleValue();
    }

    private double roundToTwoDecimals(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        return bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }
}
