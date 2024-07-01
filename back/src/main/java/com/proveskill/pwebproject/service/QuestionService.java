package com.proveskill.pwebproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.proveskill.pwebproject.model.Question;
import com.proveskill.pwebproject.repository.QuestionRepository;
import com.proveskill.pwebproject.specification.QuestionSpecifications;

import io.micrometer.common.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question create(Question question) {
        try {
            if (question.getId() != null) {
                Question questionFound = this.questionRepository.findById(question.getId())
                        .orElseThrow(() -> new RuntimeException(
                                "QuestionServiceImpl - create/update: Question not found"));
                questionFound.setAlternatives(question.getAlternatives());
                questionFound.setAnswer(question.getAnswer());
                questionFound.setLevel(question.getLevel());
                questionFound.setName(question.getName());
                questionFound.setTags(question.getTags());
                questionFound.setType(question.getType());
                questionFound.setUpdatedAt(LocalDateTime.now());
                return this.questionRepository.saveAndFlush(questionFound);
            }

            question.setCreatedAt(LocalDateTime.now());
            question.setUpdatedAt(LocalDateTime.now());
            return this.questionRepository.save(question);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(
                    "QuestionServiceImpl - create: Error when create question: {}", e);
        }
    }

    public void delete(Integer id) {
        Optional<Question> questionEntity = this.questionRepository.findById(id);
        if (!questionEntity.isPresent()) {
            throw new RuntimeException("QuestionServiceImpl - delete: Question not found");
        }

        try {
            this.questionRepository.delete(questionEntity.get());
        } catch (Exception e) {
            throw new RuntimeException(
                    "QuestionServiceImpl - delete: Error when delete question: {}", e);
        }
    }

    public List<Question> findAll() {
        List<Question> questionEntities = this.questionRepository.findAll();
        return questionEntities.stream().map(this::formatQuestion).toList();
    }

    public Question findById(Integer id) {
        return this.questionRepository.findById(id).map(this::formatQuestion).orElseThrow(
                () -> new RuntimeException("QuestionServiceImpl - findById: Question not found"));
    }

    public List<Question> searchQuestions(String keyword) {
        if (StringUtils.isEmpty(keyword)) {
            return questionRepository.findAll();
        } else {
            Specification<Question> spec = QuestionSpecifications.searchByFields(keyword);
            return questionRepository.findAll(spec);
        }
    }

    private Question formatQuestion(Question question) {
        question.setUser(null);
        return question;
    }
}
