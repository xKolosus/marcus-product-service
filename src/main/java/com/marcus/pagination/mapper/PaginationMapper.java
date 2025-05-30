package com.marcus.pagination.mapper;

import static java.util.Objects.isNull;

import com.marcus.pagination.domain.model.PageCount;
import com.marcus.pagination.domain.model.Pageable;
import java.util.function.Function;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public interface PaginationMapper<T, E> {

  static PageCount toDomain(org.springframework.data.domain.Page<?> page) {
    return new PageCount(
        page.getPageable().getPageSize(),
        page.getContent().size(),
        page.getTotalElements(),
        page.getPageable().getPageNumber(),
        page.getTotalPages());
  }

  static PageRequest toPageRequest(com.marcus.pagination.domain.model.PageRequest request) {
    PageRequest pageRequest;

    if (isNull(request.getOrderBy())) {
      pageRequest = PageRequest.of(request.getPage(), request.getPageSize());
    } else {

      pageRequest =
          PageRequest.of(
              request.getPage(),
              request.getPageSize(),
              Sort.by(request.getOrder(), request.getOrderBy()));
    }
    return pageRequest;
  }

  Pageable<T> toDomain(org.springframework.data.domain.Page<E> page, Function<E, T> contentMapping);
}
