package com.api.divideai.event.application.services.user;

import com.api.divideai.event.application.dto.PageDTO;
import com.api.divideai.event.domain.dtos.user.UserRequestDTO;
import com.api.divideai.event.domain.dtos.user.UserResponseDTO;
import org.springframework.data.domain.Page;

public interface UserService {

    UserResponseDTO findById(String id);

    Page<UserResponseDTO> findAll(PageDTO pageable);

    UserResponseDTO create(UserRequestDTO createDTO);

    UserResponseDTO update(String id, UserRequestDTO updateDTO);

    void delete(String id);
}
