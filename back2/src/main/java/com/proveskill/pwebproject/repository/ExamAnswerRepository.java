package com.proveskill.pwebproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proveskill.pwebproject.model.ExamAnswer;
import com.proveskill.pwebproject.model.Question;
import com.proveskill.pwebproject.model.StartedExam;

public interface ExamAnswerRepository extends JpaRepository<ExamAnswer, Integer> {
    Optional<ExamAnswer> findByStartedExamAndQuestion(StartedExam startedExam, Question question);

}
