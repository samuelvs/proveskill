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
    public String getAllQuestions(Model model) {
        log.info("QuestionController - getAllQuestions: Get all questions");
        List<QuestionDto> questions = this.questionService.findAll();
        model.addAttribute("route", "questions");
        model.addAttribute("questions", questions);
        return "questions/all";
    }

    @GetMapping("/questions/{id}")
    public String getQuestionById(@PathVariable("id") long id, Model model) {
        log.info("QuestionController - getQuestionById: Get question with id: {}", id);
        QuestionDto questionDto = this.questionService.findById(id);
        model.addAttribute("question", questionDto);
        model.addAttribute("route", "questions");
        return "questions/new";
    }

    @GetMapping("/questions/new")
    public String newQuestion(Model model) {
        log.info("QuestionController - newQuestion: New question");
        model.addAttribute("route", "questions");
        return "questions/new";
    }

    @PutMapping(value = PathConstants.QUESTIONS, consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> saveQuestion(@RequestBody QuestionDto questionDto, UriComponentsBuilder uriComponentsBuilder) throws Exception {
        log.info("QuestionController - createQuestion: Create question");
        QuestionDto createdQuestion = this.questionService.create(questionDto);
        URI uri = uriComponentsBuilder.path("questions/{id}").buildAndExpand(createdQuestion.getId()).toUri();
        return ResponseEntity.created(uri).body(createdQuestion);
    }

    @DeleteMapping(value = PathConstants.QUESTIONS + "/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable("id") long id) {
        log.info("QuestionController - deleteQuestion: Delete question with id: {}", id);
        this.questionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
