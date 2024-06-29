package com.proveskill.pwebproject.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.proveskill.pwebproject.constants.PathConstants;
import com.proveskill.pwebproject.model.Question;
import com.proveskill.pwebproject.model.User;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.proveskill.pwebproject.service.QuestionService;

import java.util.List;

@RestController
@RequestMapping(PathConstants.BASE_PATH)
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping(PathConstants.QUESTIONS)
    public ResponseEntity<List<Question>> getAllQuestions(Model model) {
        List<Question> questions = this.questionService.findAll();
        return ResponseEntity.ok(questions);
    }

    
    @GetMapping(PathConstants.QUESTIONS + "/{id}")
    @Cacheable("questions")
    public ResponseEntity<Question> getQuestionById(@PathVariable("id") Integer id, Model model) {
        Question question = this.questionService.findById(id);
        return ResponseEntity.ok(question);
    }

    @GetMapping(PathConstants.QUESTIONS + "/search/{search}")
    public ResponseEntity<List<Question>> getQuestionBySearch(@PathVariable("search") String search, Model model) {
        List<Question> questions = this.questionService.searchQuestions(search);
        return ResponseEntity.ok(questions);
    }

    @PutMapping(value = PathConstants.QUESTIONS, consumes = "application/json", produces = "application/json")
    @CacheEvict(cacheNames = "questions", allEntries = true)
    public ResponseEntity<Object> saveQuestion(@RequestBody Question questionDto) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        questionDto.setUser(user);
        Question createdQuestion = this.questionService.create(questionDto);
        return ResponseEntity.ok(createdQuestion);
    }

    @DeleteMapping(PathConstants.QUESTIONS + "/{id}")
    @CacheEvict(cacheNames = "questions", allEntries = true)
    public ResponseEntity<Void> deleteQuestion(@PathVariable("id") Integer id) {
        this.questionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
