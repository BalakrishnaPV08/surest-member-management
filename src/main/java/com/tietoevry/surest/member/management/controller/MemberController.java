package com.tietoevry.surest.member.management.controller;

import com.tietoevry.surest.member.management.dto.CreateMemberRequest;
import com.tietoevry.surest.member.management.dto.MemberDto;
import com.tietoevry.surest.member.management.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberService service;
    public MemberController(MemberService service) { this.service = service; }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public Page<MemberDto> list(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "lastName,asc") String sort,
                                @RequestParam(required = false) String firstName,
                                @RequestParam(required = false) String lastName) {
        String[] sortParts = sort.split(",");
        Sort.Direction dir = Sort.Direction.fromString(sortParts.length>1?sortParts[1] : "asc");
        PageRequest pr = PageRequest.of(page, size, Sort.by(dir, sortParts[0]));
        return service.search(firstName, lastName, pr);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public MemberDto get(@PathVariable UUID id) { return service.getById(id); }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<MemberDto> create(@Valid @RequestBody CreateMemberRequest req) {
        MemberDto dto = new MemberDto(); dto.firstName = req.firstName; dto.lastName = req.lastName; dto.dateOfBirth = req.dateOfBirth; dto.email = req.email;
        MemberDto created = service.create(dto);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public MemberDto update(@PathVariable UUID id, @Valid @RequestBody CreateMemberRequest req) {
        MemberDto dto = new MemberDto(); dto.firstName = req.firstName; dto.lastName = req.lastName; dto.dateOfBirth = req.dateOfBirth; dto.email = req.email;
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) { service.delete(id); return ResponseEntity.noContent().build(); }
}

