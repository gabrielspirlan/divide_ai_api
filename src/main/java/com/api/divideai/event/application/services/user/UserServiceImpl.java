package com.api.divideai.event.application.services.user;

import com.api.divideai.event.application.dto.PageDTO;
import com.api.divideai.event.domain.collections.User;
import com.api.divideai.event.domain.dtos.user.UserRequestDTO;
import com.api.divideai.event.domain.dtos.user.UserResponseDTO;
import com.api.divideai.event.exceptions.DuplicateEmailException;
import com.api.divideai.event.infrastructure.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO findById(String id) {
        return mapper.map(userRepository.findById(id), UserResponseDTO.class);
    }

    @Override
    public Page<UserResponseDTO> findAll(PageDTO pageDTO) {

        pageDTO.sortByName();
        Page<User> usersPage = userRepository.findAll(pageDTO.mapPage());
        List<UserResponseDTO> userDTOList = usersPage.getContent().stream()
                .map(user -> mapper.map(user, UserResponseDTO.class))
                .toList();

        return new PageImpl<UserResponseDTO>(userDTOList, pageDTO.mapPage(), usersPage.getTotalElements());
    }

    @Override
    public UserResponseDTO create(UserRequestDTO createDTO) {

        Optional<User> existingUser = userRepository.findByEmail(createDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new DuplicateEmailException("Usuário já cadastrado com este email");
        }

        String encodedPassword = passwordEncoder.encode(createDTO.getPassword());
        createDTO.setPassword(encodedPassword);

        User user = mapper.map(createDTO, User.class);

        return mapper.map(userRepository.save(user), UserResponseDTO.class);
    }

    @Override
    public UserResponseDTO update(String id, UserRequestDTO updateDTO) {

       User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário com o id: " + id + "não encontrado"));

        if(updateDTO == null) {
            throw  new RuntimeException("Dados do usuário não enviados");
        }

        if (updateDTO.getEmail() != null) {
            user.setEmail(updateDTO.getEmail());
        }
        if(updateDTO.getName() != null) {
            user.setName(updateDTO.getName());
        }

        if(updateDTO.getPassword() != null) {
            String encodedPassword = passwordEncoder.encode(updateDTO.getPassword());
            user.setPassword(encodedPassword);
        }

        return mapper.map(userRepository.save(user), UserResponseDTO.class);
    }

    @Override
    public void delete(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário com o id: " + id + "não encontrado"));
        userRepository.delete(user);
    }
}
