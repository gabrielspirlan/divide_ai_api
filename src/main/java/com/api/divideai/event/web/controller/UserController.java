package com.api.divideai.event.web.controller;

import com.api.divideai.event.application.dto.PageDTO;
import com.api.divideai.event.application.services.user.UserService;
import com.api.divideai.event.domain.dtos.user.UserRequestDTO;
import com.api.divideai.event.domain.dtos.user.UserResponseDTO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;



    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getOne(@PathVariable String id) {
        return new ResponseEntity<>(
                userService.findById(id),
                HttpStatus.OK
        );
    }

    @GetMapping()
    public ResponseEntity<Page<UserResponseDTO>> getMany (@ParameterObject PageDTO pageDTO) {
        return new ResponseEntity<>(
                userService.findAll(pageDTO),
                HttpStatus.OK
        );
    }

    @PostMapping()
    public ResponseEntity<UserResponseDTO> create (@RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(
                userService.create(userRequestDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update (@PathVariable String id, @RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(
                userService.update(id ,userRequestDTO),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        userService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }


}
