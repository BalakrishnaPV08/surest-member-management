package com.tietoevry.surest.member.management.util;

import org.springframework.data.domain.Page;

import java.util.Map;

public class PaginationUtil {
    private PaginationUtil() {}
    public static <T> Map<String, Object> toResponse(Page<T> page) {
        return Map.of(
                "content", page.getContent(),
                "page", page.getNumber(),
                "size", page.getSize(),
                "totalElements", page.getTotalElements(),
                "totalPages", page.getTotalPages()
        );
    }
}

