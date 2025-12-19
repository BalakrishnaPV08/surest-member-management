package com.tietoevry.surest.member.management.service;

import com.tietoevry.surest.member.management.dto.MemberDto;
import com.tietoevry.surest.member.management.entity.Member;
import com.tietoevry.surest.member.management.exception.ApiException;
import com.tietoevry.surest.member.management.mapper.MemberMapper;
import com.tietoevry.surest.member.management.repository.MemberRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper mapper;

    public MemberService(MemberRepository memberRepository, MemberMapper mapper) {
        this.memberRepository = memberRepository; this.mapper = mapper;
    }

    public Page<MemberDto> search(String firstName, String lastName, Pageable pageable) {
        if (firstName == null) firstName = "";
        if (lastName == null) lastName = "";
        return memberRepository.findByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(firstName, lastName, pageable)
                .map(mapper::toDto);
    }

    @Cacheable(value = "members", key = "#id")
    public MemberDto getById(UUID id) {
        return memberRepository.findById(id).map(mapper::toDto).orElseThrow(() -> new ApiException.NotFound("Member not found"));
    }

    @Transactional
    public MemberDto create(MemberDto dto) {
        memberRepository.findByEmail(dto.email).ifPresent(m -> { throw new ApiException.Conflict("Email already exists"); });
        Member m = mapper.toEntity(dto);
        Member saved = memberRepository.save(m);
        return mapper.toDto(saved);
    }

    @Transactional
    @CacheEvict(value = "members", key = "#id")
    public MemberDto update(UUID id, MemberDto dto) {
        Member existing = memberRepository.findById(id).orElseThrow(() -> new ApiException.NotFound("Member not found"));
        existing.setFirstName(dto.firstName); existing.setLastName(dto.lastName); existing.setDateOfBirth(dto.dateOfBirth); existing.setEmail(dto.email);
        Member saved = memberRepository.save(existing);
        return mapper.toDto(saved);
    }

    @Transactional
    @CacheEvict(value = "members", key = "#id")
    public void delete(UUID id) {
        Member existing = memberRepository.findById(id).orElseThrow(() -> new ApiException.NotFound("Member not found"));
        memberRepository.delete(existing);
    }
}

