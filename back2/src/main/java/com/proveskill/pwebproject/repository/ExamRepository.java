package com.proveskill.pwebproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proveskill.pwebproject.model.Exam;

public interface ExamRepository extends JpaRepository<Exam, Integer> {

}
