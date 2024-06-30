package com.proveskill.pwebproject.auth;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnswerRequest {

  @JsonProperty("startedExamId")
  private Long startedExamId;

  @JsonProperty("questionId")
  private Integer questionId;

  @JsonProperty("answer")
  private List<String> answer;
}
