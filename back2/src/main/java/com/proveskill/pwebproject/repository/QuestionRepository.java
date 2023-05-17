package com.proveskill.pwebproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proveskill.pwebproject.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
