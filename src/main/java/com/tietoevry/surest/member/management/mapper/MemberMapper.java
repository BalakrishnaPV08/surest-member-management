package com.tietoevry.surest.member.management.mapper;

import com.tietoevry.surest.member.management.dto.MemberDto;
import com.tietoevry.surest.member.management.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {

    // ENTITY → DTO
    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "dateOfBirth", source = "dateOfBirth")
    @Mapping(target = "email", source = "email")
    MemberDto toDto(Member member);

    // DTO → ENTITY
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Member toEntity(MemberDto dto);
}
