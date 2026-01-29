package com.tietoevry.surest.member.management.service;

import com.tietoevry.surest.member.management.dto.MemberDto;
import com.tietoevry.surest.member.management.entity.Member;
import com.tietoevry.surest.member.management.exception.ApiException;
import com.tietoevry.surest.member.management.mapper.MemberMapper;
import com.tietoevry.surest.member.management.repository.MemberRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MemberServiceTest {

    private MemberRepository memberRepository;
    private MemberMapper mapper;
    private MemberService service;

    @BeforeEach
    void setup() {
        memberRepository = mock(MemberRepository.class);
        mapper = mock(MemberMapper.class);
        service = new MemberService(memberRepository, mapper);
    }

    @Test
    void getById_success() {
        UUID id = UUID.randomUUID();
        Member member = new Member();
        member.setId(id);

        MemberDto dto = new MemberDto();
        dto.id = id;

        when(memberRepository.findById(id)).thenReturn(Optional.of(member));
        when(mapper.toDto(member)).thenReturn(dto);

        MemberDto result = service.getById(id);

        assertEquals(id, result.id);
    }

    @Test
    void getById_notFound() {
        when(memberRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ApiException.NotFound.class, () -> service.getById(UUID.randomUUID()));
    }

    @Test
    void create_success() {
        MemberDto dto = new MemberDto();
        dto.firstName = "John";
        dto.lastName = "Doe";
        dto.dateOfBirth = LocalDate.of(1990, 1, 1);
        dto.email = "john@mail.com";

        Member saved = new Member();
        saved.setId(UUID.randomUUID());
        saved.setEmail("john@mail.com");

        when(memberRepository.findByEmail("john@mail.com")).thenReturn(Optional.empty());
        when(mapper.toEntity(dto)).thenReturn(saved);
        when(memberRepository.save(saved)).thenReturn(saved);
        when(mapper.toDto(saved)).thenReturn(dto);

        MemberDto result = service.create(dto);

        assertEquals("john@mail.com", result.email);
    }

    @Test
    void create_emailExists() {
        Member member = new Member();
        member.setEmail("existing@mail.com");

        when(memberRepository.findByEmail("existing@mail.com")).thenReturn(Optional.of(member));

        MemberDto dto = new MemberDto();
        dto.email = "existing@mail.com";

        assertThrows(ApiException.Conflict.class, () -> service.create(dto));
    }

    @Test
    void update_success() {
        UUID id = UUID.randomUUID();
        Member existing = new Member();
        existing.setId(id);

        MemberDto dto = new MemberDto();
        dto.email = "new@mail.com";

        when(memberRepository.findById(id)).thenReturn(Optional.of(existing));
        when(memberRepository.save(existing)).thenReturn(existing);
        when(mapper.toDto(existing)).thenReturn(dto);

        MemberDto updated = service.update(id, dto);

        assertEquals("new@mail.com", updated.email);
    }

    @Test
    void delete_success() {
        UUID id = UUID.randomUUID();
        Member existing = new Member();
        existing.setId(id);

        when(memberRepository.findById(id)).thenReturn(Optional.of(existing));

        service.delete(id);

        verify(memberRepository, times(1)).delete(existing);
    }
}

