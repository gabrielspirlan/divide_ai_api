package com.api.divideai.event.web.controller;

import com.api.divideai.event.application.dto.PageDTO;
import com.api.divideai.event.application.services.group.GroupService;
import com.api.divideai.event.domain.dtos.group.GroupRequestDTO;
import com.api.divideai.event.domain.dtos.group.GroupResponseDTO;
import com.api.divideai.event.domain.dtos.group.GroupTotalsResponseDTO;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @GetMapping
    public ResponseEntity<Page<GroupResponseDTO>> getMany(@ParameterObject PageDTO pageDTO) {
        return new ResponseEntity<>(
                groupService.findAll(pageDTO),
                HttpStatus.OK
        );
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<GroupResponseDTO>> getByUserId(
            @PathVariable String userId,
            @ParameterObject PageDTO pageDTO) {
        return new ResponseEntity<>(
                groupService.findByUserId(userId, pageDTO),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> getOne(@PathVariable String id) {
        return new ResponseEntity<>(
                groupService.findById(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}/totals")
    public ResponseEntity<GroupTotalsResponseDTO> getGroupTotals(@PathVariable String id) {
        return new ResponseEntity<>(
                groupService.getGroupTotals(id),
                HttpStatus.OK
        );
    }

    @PostMapping
    public ResponseEntity<GroupResponseDTO> create(@RequestBody GroupRequestDTO createDTO) {
        return new ResponseEntity<>(
                groupService.create(createDTO),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> update(@PathVariable String id, @RequestBody GroupRequestDTO updateDTO) {
        return new ResponseEntity<>(
                groupService.update(id, updateDTO),
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GroupResponseDTO> delete(@PathVariable String id) {
        groupService.delete(id);
        return new ResponseEntity<>(
                HttpStatus.OK
        );
    }
}
