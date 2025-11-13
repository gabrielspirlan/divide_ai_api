package com.api.divideai.event.application.services.group;

import com.api.divideai.event.application.dto.PageDTO;
import com.api.divideai.event.domain.dtos.group.GroupBillResponseDTO;
import com.api.divideai.event.domain.dtos.group.GroupRequestDTO;
import com.api.divideai.event.domain.dtos.group.GroupResponseDTO;
import com.api.divideai.event.domain.dtos.group.GroupTotalsResponseDTO;
import org.springframework.data.domain.Page;

public interface GroupService {

    GroupResponseDTO findById(String id);

    Page<GroupResponseDTO> findAll(PageDTO pageable);

    Page<GroupResponseDTO> findByUserId(String userId, PageDTO pageable);

    GroupTotalsResponseDTO getGroupTotals(String groupId);

    GroupBillResponseDTO getGroupBill(String groupId);

    GroupResponseDTO create(GroupRequestDTO createDTO);

    GroupResponseDTO update(String id, GroupRequestDTO updateDTO);

    void delete(String id);
}
