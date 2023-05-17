package com.proveskill.pwebproject.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proveskill.pwebproject.model.Exam;
import com.proveskill.pwebproject.model.StartedExam;
import com.proveskill.pwebproject.model.User;

public interface StartedExamRepository extends JpaRepository<StartedExam, Integer> {
    Optional<StartedExam> findByExamAndUser(Exam exam, User user);
}
