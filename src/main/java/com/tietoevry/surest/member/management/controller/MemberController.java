package com.tietoevry.surest.member.management.controller;

import com.tietoevry.surest.member.management.dto.CreateMemberRequest;
import com.tietoevry.surest.member.management.dto.MemberDto;
import com.tietoevry.surest.member.management.service.MemberService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/members")
public class MemberController {
    private final MemberService service;
    public MemberController(MemberService service) { this.service = service; }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public Page<MemberDto> list(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "lastName,asc") String sort,
                                @RequestParam(required = false) String firstName,
                                @RequestParam(required = false) String lastName) {

        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), 100);

        List<String> allowedSortFields = List.of("firstName", "lastName", "email", "id");

        String[] sortParts = sort.split(",");
        String sortField = sortParts[0];
        String sortDir = sortParts.length > 1 ? sortParts[1] : "asc";

        if (!allowedSortFields.contains(sortField)) {
            sortField = "lastName";
        }

        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        PageRequest pr = PageRequest.of(safePage, safeSize, Sort.by(direction, sortField));

        return service.search(firstName, lastName, pr);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public MemberDto get(@PathVariable UUID id) {
        log.info("Fetching member by id={}", id);
        return service.getById(id); }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MemberDto> create(@Valid @RequestBody CreateMemberRequest req) {
        log.info("Creating member: firstName={}, lastName={}",
                req.firstName, req.lastName);
        MemberDto dto = new MemberDto(); dto.firstName = req.firstName; dto.lastName = req.lastName; dto.dateOfBirth = req.dateOfBirth; dto.email = req.email;
        MemberDto created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public MemberDto update(@PathVariable UUID id, @Valid @RequestBody CreateMemberRequest req) {
        log.info("Updating member id={}, firstName={}, lastName={}",
                id, req.firstName, req.lastName);
        MemberDto dto = new MemberDto(); dto.firstName = req.firstName; dto.lastName = req.lastName; dto.dateOfBirth = req.dateOfBirth; dto.email = req.email;
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        log.warn("Deleting member with id={}", id);
        service.delete(id); return ResponseEntity.noContent().build(); }
}

