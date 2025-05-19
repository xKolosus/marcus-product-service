package com.marcus.pagination.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageRequest {

  private int page = 0;
  private int pageSize = 15;
  private String orderBy;
  private Sort.Direction order = Sort.Direction.ASC;
}
