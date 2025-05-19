package com.marcus.pagination.domain.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Page<T> {

  private List<T> content;
  private PageCount page;
}
