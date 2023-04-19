package com.proveskill.controller.api;

import com.proveskill.constants.PathConstants;
import com.proveskill.model.dto.QuestionDto;
import com.proveskill.service.question.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Controller
@Slf4j
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questions")
    public ResponseEntity<List<QuestionDto>> getAllQuestions(Model model) {
        List<QuestionDto> questions = this.questionService.findAll();
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<QuestionDto> getQuestionById(@PathVariable("id") long id, Model model) {
        QuestionDto question = this.questionService.findById(id);
        return ResponseEntity.ok(question);
    }

    @PutMapping(value = PathConstants.QUESTIONS, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> saveQuestion(@RequestBody QuestionDto questionDto) throws Exception {
        QuestionDto createdQuestion = this.questionService.create(questionDto);
        return ResponseEntity.ok(createdQuestion);
    }

    @DeleteMapping(value = PathConstants.QUESTIONS + "/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable("id") long id) {
        this.questionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
