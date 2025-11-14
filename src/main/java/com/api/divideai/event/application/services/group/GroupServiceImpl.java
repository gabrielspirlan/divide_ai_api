package com.api.divideai.event.application.services.group;

import com.api.divideai.event.application.dto.PageDTO;
import com.api.divideai.event.domain.collections.Group;
import com.api.divideai.event.domain.collections.Transaction;
import com.api.divideai.event.domain.collections.User;
import com.api.divideai.event.domain.dtos.group.GroupBillResponseDTO;
import com.api.divideai.event.domain.dtos.group.GroupRequestDTO;
import com.api.divideai.event.domain.dtos.group.GroupResponseDTO;
import com.api.divideai.event.domain.dtos.group.GroupTotalsResponseDTO;
import com.api.divideai.event.domain.dtos.group.GroupUserDTO;
import com.api.divideai.event.domain.dtos.group.GroupUsersResponseDTO;
import com.api.divideai.event.domain.dtos.group.UserBillDTO;
import com.api.divideai.event.infrastructure.repositories.GroupRepository;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper mapper;

    @Override
    public GroupResponseDTO findById(String id) {
        Group group = groupRepository.findById(id).orElseThrow(() -> new RuntimeException("Grupo com o id: " + id + " não existe"));
        return mapAndSetTotalValue(group);
    }

    @Override
    public Page<GroupResponseDTO> findAll(PageDTO pageDTO) {

        pageDTO.sortByName();

        Page<Group> groupPage = groupRepository.findAll(pageDTO.mapPage());
        List<GroupResponseDTO> groupDTOList = groupPage.getContent().stream()
                .map(this::mapAndSetTotalValue)
                .toList();


        return new PageImpl<GroupResponseDTO>(groupDTOList, pageDTO.mapPage(), groupPage.getTotalElements());
    }

    @Override
    public Page<GroupResponseDTO> findByUserId(String userId, PageDTO pageDTO) {

        pageDTO.sortByName();

        Page<Group> groupPage = groupRepository.findByParticipantsContaining(userId, pageDTO.mapPage());
        List<GroupResponseDTO> groupDTOList = groupPage.getContent().stream()
                .map(this::mapAndSetTotalValue)
                .toList();

        return new PageImpl<GroupResponseDTO>(groupDTOList, pageDTO.mapPage(), groupPage.getTotalElements());
    }

    @Override
    public GroupTotalsResponseDTO getGroupTotals(String groupId) {
        // Verificar se o grupo existe
        groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo com o id: " + groupId + " não existe"));

        // Buscar todas as transações do grupo
        Page<Transaction> transactionsPage = transactionRepository.findByGroup(groupId, Pageable.unpaged());
        List<Transaction> transactions = transactionsPage.getContent();

        double totalExpenses = 0.0;
        double individualExpenses = 0.0;
        double sharedExpenses = 0.0;

        for (Transaction transaction : transactions) {
            double value = transaction.getValue() != null ? transaction.getValue() : 0.0;
            totalExpenses += value;

            // Se tem mais de um participante, é compartilhado
            if (transaction.getParticipants() != null && transaction.getParticipants().size() > 1) {
                sharedExpenses += value;
            } else {
                individualExpenses += value;
            }
        }

        // Arredondar valores para 2 casas decimais
        totalExpenses = roundToTwoDecimals(totalExpenses);
        individualExpenses = roundToTwoDecimals(individualExpenses);
        sharedExpenses = roundToTwoDecimals(sharedExpenses);

        return new GroupTotalsResponseDTO(totalExpenses, individualExpenses, sharedExpenses);
    }

    @Override
    public GroupBillResponseDTO getGroupBill(String groupId) {
        // Verificar se o grupo existe
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo com o id: " + groupId + " não existe"));

        // Buscar todas as transações do grupo
        Page<Transaction> transactionsPage = transactionRepository.findByGroup(groupId, Pageable.unpaged());
        List<Transaction> transactions = transactionsPage.getContent();

        // Mapa para armazenar as despesas de cada usuário
        Map<String, UserBillData> userBillDataMap = new HashMap<>();

        // Inicializar dados para cada participante do grupo
        for (String participantId : group.getParticipants()) {
            User user = userRepository.findById(participantId)
                    .orElse(null);
            String userName = user != null ? user.getName() : "Usuário não encontrado";
            userBillDataMap.put(participantId, new UserBillData(participantId, userName));
        }

        double totalExpenses = 0.0;
        double totalIndividualExpenses = 0.0;
        double totalSharedExpenses = 0.0;

        // Processar cada transação
        for (Transaction transaction : transactions) {
            double value = transaction.getValue() != null ? transaction.getValue() : 0.0;
            totalExpenses += value;

            List<String> participants = transaction.getParticipants();

            if (participants != null && !participants.isEmpty()) {
                // Se tem mais de um participante, é compartilhado
                if (participants.size() > 1) {
                    totalSharedExpenses += value;
                    // Dividir o valor igualmente entre os participantes
                    double sharedPerPerson = value / participants.size();

                    for (String participantId : participants) {
                        UserBillData billData = userBillDataMap.get(participantId);
                        if (billData != null) {
                            billData.addSharedExpense(sharedPerPerson);
                        }
                    }
                } else {
                    // Despesa individual
                    totalIndividualExpenses += value;
                    String participantId = participants.get(0);
                    UserBillData billData = userBillDataMap.get(participantId);
                    if (billData != null) {
                        billData.addIndividualExpense(value);
                    }
                }
            }
        }

        // Criar lista de UserBillDTO
        List<UserBillDTO> userBills = new ArrayList<>();
        for (UserBillData billData : userBillDataMap.values()) {
            UserBillDTO userBill = new UserBillDTO(
                    billData.userId,
                    billData.userName,
                    roundToTwoDecimals(billData.individualExpenses),
                    roundToTwoDecimals(billData.sharedExpenses),
                    roundToTwoDecimals(billData.individualExpenses + billData.sharedExpenses)
            );
            userBills.add(userBill);
        }

        // Criar e retornar o DTO de resposta
        return new GroupBillResponseDTO(
                group.getId(),
                group.getName(),
                roundToTwoDecimals(totalExpenses),
                roundToTwoDecimals(totalIndividualExpenses),
                roundToTwoDecimals(totalSharedExpenses),
                userBills
        );
    }

    @Override
    public GroupUsersResponseDTO getGroupUsers(String groupId) {
        // Verificar se o grupo existe
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Grupo com o id: " + groupId + " não existe"));

        // Buscar informações de cada participante
        List<GroupUserDTO> users = new ArrayList<>();

        if (group.getParticipants() != null && !group.getParticipants().isEmpty()) {
            for (String participantId : group.getParticipants()) {
                User user = userRepository.findById(participantId).orElse(null);

                if (user != null) {
                    GroupUserDTO userDTO = new GroupUserDTO(
                            user.getId(),
                            user.getName(),
                            user.getEmail()
                    );
                    users.add(userDTO);
                }
            }
        }

        return new GroupUsersResponseDTO(
                group.getId(),
                group.getName(),
                users
        );
    }

    @Override
    public GroupResponseDTO create(GroupRequestDTO createDTO) {
        Group group = groupRepository.save(mapper.map(createDTO, Group.class));
        return mapAndSetTotalValue(group);
    }

    @Override
    public GroupResponseDTO update(String id, GroupRequestDTO updateDTO) {

        Group group = groupRepository.findById(id).orElseThrow(() -> new RuntimeException("Grupo com o id: " + id + "não existe"));

        if (updateDTO.getName() != null && !updateDTO.getName().isEmpty()) {
            group.setName(updateDTO.getName());
        }

        if (updateDTO.getBackgroundIconColor() != null && !updateDTO.getBackgroundIconColor().isEmpty()) {
            group.setBackgroundIconColor(updateDTO.getBackgroundIconColor());
        }

        if(updateDTO.getDescription() != null && !updateDTO.getDescription().isEmpty()) {
            group.setDescription(updateDTO.getDescription());
        }

        if(updateDTO.getParticipants() != null) {
            group.setParticipants(updateDTO.getParticipants());
        }

        Group groupUpdate = groupRepository.save(group);

        return mapAndSetTotalValue(groupUpdate);
    }

    @Override
    public void delete(String id) {
        Group group = groupRepository.findById(id).orElseThrow(() -> new RuntimeException("Grupo com o id: " + id + "não existe"));

        // Deletar todas as transações vinculadas ao grupo
        transactionRepository.deleteByGroup(id);

        // Deletar o grupo
        groupRepository.delete(group);
    }

    private GroupResponseDTO mapAndSetTotalValue (Group group) {
        Double totalTransactions = transactionRepository.sumValueByGroup(group.getId());
        GroupResponseDTO map = mapper.map(group, GroupResponseDTO.class);
        map.setTotalTransactions(totalTransactions != null ? totalTransactions : 0);

        // Buscar nomes dos participantes
        if (group.getParticipants() != null && !group.getParticipants().isEmpty()) {
            List<String> participantNames = getParticipantNames(group.getParticipants());
            map.setParticipantNames(participantNames);
        }

        return map;
    }

    private List<String> getParticipantNames(List<String> participantIds) {
        return participantIds.stream()
                .map(id -> userRepository.findById(id)
                        .map(user -> user.getName())
                        .orElse("Usuário não encontrado"))
                .toList();
    }

    private double roundToTwoDecimals(double value) {
        BigDecimal bd = BigDecimal.valueOf(value);
        return bd.setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    // Classe auxiliar para armazenar dados temporários da bill de cada usuário
    private static class UserBillData {
        String userId;
        String userName;
        double individualExpenses;
        double sharedExpenses;

        UserBillData(String userId, String userName) {
            this.userId = userId;
            this.userName = userName;
            this.individualExpenses = 0.0;
            this.sharedExpenses = 0.0;
        }

        void addIndividualExpense(double amount) {
            this.individualExpenses += amount;
        }

        void addSharedExpense(double amount) {
            this.sharedExpenses += amount;
        }
    }
}
