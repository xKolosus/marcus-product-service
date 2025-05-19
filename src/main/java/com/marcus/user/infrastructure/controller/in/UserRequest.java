package com.marcus.user.infrastructure.controller.in;

import com.marcus.pagination.domain.model.PageRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest extends PageRequest {

  private String searchBy;
}
