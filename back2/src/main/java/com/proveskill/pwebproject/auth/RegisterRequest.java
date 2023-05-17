package com.proveskill.pwebproject.auth;

import com.proveskill.pwebproject.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String name;
  private String email;
  private String password;
  private String school;
  private Role role;
}
