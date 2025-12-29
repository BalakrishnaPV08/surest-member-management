package com.tietoevry.surest.member.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tietoevry.surest.member.management.config.TestSecurityConfig;
import com.tietoevry.surest.member.management.dto.CreateMemberRequest;
import com.tietoevry.surest.member.management.dto.MemberDto;
import com.tietoevry.surest.member.management.exception.ErrorMessagesConfig;
import com.tietoevry.surest.member.management.service.MemberService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
        controllers = MemberController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {
                        com.tietoevry.surest.member.management.config.SecurityConfig.class,
                        com.tietoevry.surest.member.management.config.JwtAuthFilter.class
                }
        )
)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ErrorMessagesConfig errorMessagesConfig;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MemberService memberService;

    /* -------------------- GET /members -------------------- */

    @Test
    @WithMockUser(roles = "USER")
    void list_returnsPage() throws Exception {
        MemberDto dto = new MemberDto();
        dto.firstName = "Balu";

        PageRequest pr = PageRequest.of(
                0, 10, Sort.by(Sort.Direction.ASC, "lastName")
        );

        when(memberService.search(null, null, pr))
                .thenReturn(new PageImpl<>(List.of(dto)));

        mockMvc.perform(get("/api/v1/members")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "lastName,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].firstName").value("Balu"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getById_returnsMember() throws Exception {
        UUID id = UUID.randomUUID();

        MemberDto dto = new MemberDto();
        dto.id = id;
        dto.firstName = "Balu";

        when(memberService.getById(id)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/members/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Balu"));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void create_returns201() throws Exception {
        CreateMemberRequest req = new CreateMemberRequest();
        req.firstName = "Balu";
        req.lastName = "Krishna";
        req.email = "balu@test.com";
        req.dateOfBirth = LocalDate.now();

        MemberDto created = new MemberDto();
        created.firstName = "Balu";

        when(memberService.create(any(MemberDto.class)))
                .thenReturn(created);

        mockMvc.perform(post("/api/v1/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Balu"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void update_returnsUpdatedMember() throws Exception {
        UUID id = UUID.randomUUID();

        CreateMemberRequest req = new CreateMemberRequest();
        req.firstName = "Updated";
        req.lastName = "Krishna";
        req.email = "updated@test.com";
        req.dateOfBirth = LocalDate.of(1995, 1, 1);

        MemberDto updated = new MemberDto();
        updated.firstName = "Updated";

        when(memberService.update(eq(id), any(MemberDto.class)))
                .thenReturn(updated);

        mockMvc.perform(put("/api/v1/members/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"));
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void delete_returns204() throws Exception {
        UUID id = UUID.randomUUID();

        doNothing().when(memberService).delete(id);

        mockMvc.perform(delete("/api/v1/members/{id}", id))
                .andExpect(status().isNoContent());
    }
}
