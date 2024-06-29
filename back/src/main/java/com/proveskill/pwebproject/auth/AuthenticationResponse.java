package com.proveskill.pwebproject.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.proveskill.pwebproject.user.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  @JsonProperty("token")
  private String token;

  @JsonProperty("password")
  private String password;

  @JsonProperty("name")
  private String name;

  @JsonProperty("email")
  private String email;

  @JsonProperty("school")
  private String school;

  @JsonProperty("first_acsess")
  private boolean first_access;

  @JsonProperty("role")
  private Role role;
}
