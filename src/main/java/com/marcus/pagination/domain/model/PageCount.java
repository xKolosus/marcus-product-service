package com.marcus.pagination.domain.model;

public record PageCount(
    int pageSize, int pageContent, long totalContent, long pageNumber, long totalPages) {}
