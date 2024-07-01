package com.proveskill.pwebproject.service;

import com.proveskill.pwebproject.model.Question;
import com.proveskill.pwebproject.repository.QuestionRepository;
import io.micrometer.common.util.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateQuestion_WithNewQuestion() {
        Question question = new Question();
        question.setName("Test Question");
        question.setAnswer(Arrays.asList("Option A", "Option B"));
        question.setType(1);
        question.setLevel(1);

        when(questionRepository.save(any())).thenReturn(question);

        Question createdQuestion = questionService.create(question);
        assertEquals("Test Question", createdQuestion.getName());
        assertEquals(Arrays.asList("Option A", "Option B"), createdQuestion.getAnswer());
        assertEquals(1, createdQuestion.getLevel());
        assertEquals(1, createdQuestion.getType());
        assertNotNull(createdQuestion.getCreatedAt());
        assertNotNull(createdQuestion.getUpdatedAt());
    }

    @Test
    void testCreateQuestion_ExceptionWhenSaving() {
        Question question = new Question();
        question.setName("Test Question");

        when(questionRepository.save(any()))
                .thenThrow(new RuntimeException("Failed to save question"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            questionService.create(question);
        });

        assertEquals("QuestionServiceImpl - create: Error when create question: {}",
                exception.getMessage());
    }

    @Test
    void testDeleteQuestion_ExistingQuestion() {
        Question question = new Question();
        question.setId(1);

        Optional<Question> optionalQuestion = Optional.of(question);
        when(questionRepository.findById(1)).thenReturn(optionalQuestion);

        questionService.delete(1);

        verify(questionRepository, times(1)).delete(question);
    }

    @Test
    void testDeleteQuestion_NonExistingQuestion() {
        int nonExistingId = 999;
        when(questionRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            questionService.delete(nonExistingId);
        });

        assertEquals("QuestionServiceImpl - delete: Question not found", exception.getMessage());
    }

    @Test
    void testFindAllQuestions() {
        List<Question> mockQuestions = new ArrayList<>();
        mockQuestions.add(new Question());
        mockQuestions.add(new Question());

        when(questionRepository.findAll()).thenReturn(mockQuestions);

        List<Question> foundQuestions = questionService.findAll();

        assertEquals(2, foundQuestions.size());
    }

    @Test
    void testFindQuestionById() {
        Question question = new Question();
        question.setId(1);

        Optional<Question> optionalQuestion = Optional.of(question);
        when(questionRepository.findById(1)).thenReturn(optionalQuestion);

        Question foundQuestion = questionService.findById(1);

        assertEquals(1, foundQuestion.getId());
    }

    @Test
    void testFindQuestionById_NotFound() {
        int nonExistingId = 999;
        when(questionRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            questionService.findById(nonExistingId);
        });

        assertEquals("QuestionServiceImpl - findById: Question not found", exception.getMessage());
    }

    @Test
    void testSearchQuestions_WithoutKeyword() {
        List<Question> mockQuestions = new ArrayList<>();
        mockQuestions.add(new Question());
        mockQuestions.add(new Question());

        when(StringUtils.isEmpty(null)).thenReturn(true);
        when(questionRepository.findAll()).thenReturn(mockQuestions);

        List<Question> foundQuestions = questionService.searchQuestions(null);

        assertEquals(2, foundQuestions.size());
    }
}
