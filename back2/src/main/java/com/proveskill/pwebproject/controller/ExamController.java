package com.proveskill.pwebproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.proveskill.pwebproject.auth.AnswerRequest;
import com.proveskill.pwebproject.constants.PathConstants;
import com.proveskill.pwebproject.model.Exam;
import com.proveskill.pwebproject.model.ExamAnswer;
import com.proveskill.pwebproject.model.Question;
import com.proveskill.pwebproject.model.StartedExam;
import com.proveskill.pwebproject.model.User;
import com.proveskill.pwebproject.service.ExamService;
import com.proveskill.pwebproject.user.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(PathConstants.BASE_PATH)
@RequiredArgsConstructor
public class ExamController {
    private final ExamService examService;

    @GetMapping(PathConstants.EXAMS)
    public Object getAllExams(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        List<Exam> exams = this.examService.findAll();

        if (user.getRole().equals(Role.STUDENT)) {
            List<Exam> selectedColumns = new ArrayList<>();
            for (Exam exam : exams) {
                Exam selectedExam = new Exam();
                selectedExam.setId(exam.getId());
                selectedExam.setTitle(exam.getTitle());
                selectedExam.setDuration(exam.getDuration());
                selectedExam.setStartDateTime(exam.getStartDateTime());
                selectedExam.setEndDateTime(exam.getEndDateTime());
                selectedColumns.add(selectedExam);
            }
            return selectedColumns;
        }

        return ResponseEntity.ok(exams);
    }

    @PutMapping(value = PathConstants.EXAMS, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> saveExam(@RequestBody @Valid Exam exam) throws Exception {
        Exam createdExam = this.examService.create(exam);
        return ResponseEntity.ok(createdExam);
    }

    @DeleteMapping(PathConstants.EXAMS + "/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable("id") Integer id) {
        this.examService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(PathConstants.EXAMS + "/{id}")
    public ResponseEntity<Exam> getExamById(@PathVariable("id") Integer id) {
        Exam exam = this.examService.findById(id);
        return ResponseEntity.ok(exam);
    }

    @PutMapping(value = PathConstants.EXAMS + "/answer", produces = "application/json")
    public ResponseEntity<List<String>> answerExam(@RequestBody AnswerRequest request) throws Exception {
        System.out.println("chegoou");
        ExamAnswer examAnswer = this.examService.answerExam(request.getStarted_exam_id(), request.getQuestion_id(), request.getAnswer());
        return ResponseEntity.ok(examAnswer.getAnswer());
    }

    @GetMapping(value = PathConstants.EXAMS + "/start/{examId}/{userId}", produces = "application/json")
    public ResponseEntity<StartedExam> startExam(@PathVariable("examId") Integer examId, @PathVariable("userId") Integer userId) {
        StartedExam startedExam = this.examService.startExam(examId, userId);
        return ResponseEntity.ok(startedExam);
    }
}
