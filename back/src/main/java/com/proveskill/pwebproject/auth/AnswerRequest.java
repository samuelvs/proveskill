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

  @JsonProperty("started_exam_id")
  private Long started_exam_id;

  @JsonProperty("question_id")
  private Integer question_id;

  @JsonProperty("answer")
  private List<String> answer;
}
