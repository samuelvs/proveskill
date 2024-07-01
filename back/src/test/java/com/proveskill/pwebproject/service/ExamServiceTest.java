package com.proveskill.pwebproject.service;

import com.proveskill.pwebproject.model.*;
import com.proveskill.pwebproject.repository.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ExamServiceTest {

    @Mock
    private ExamRepository examRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private StartedExamRepository startedExamRepository;
    @Mock
    private ExamAnswerRepository examAnswerRepository;

    @InjectMocks
    private ExamService examService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private Exam createMockExam(int questionId) {
        Exam exam = new Exam();
        Question question = new Question();
        question.setId(questionId);
        exam.setQuestions(Collections.singletonList(question));
        return exam;
    }

    private User createMockUser() {
        User user = new User();
        user.setId(1);
        return user;
    }

    private Question createMockQuestion(int questionId) {
        Question question = new Question();
        question.setId(questionId);
        return question;
    }

    @Test
    void testCreateNewExam() {
        Exam exam = createMockExam(1);
        Question question = exam.getQuestions().get(0);

        when(questionRepository.findById(1)).thenReturn(Optional.of(question));
        when(examRepository.save(any(Exam.class))).thenReturn(exam);

        Exam createdExam = examService.create(exam);

        assertNotNull(createdExam);
        verify(questionRepository, times(1)).findById(1);
        verify(examRepository, times(1)).save(exam);
    }

    @Test
    void testCreateExamQuestionNotFound() {
        Exam exam = createMockExam(1);

        when(questionRepository.findById(1)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            examService.create(exam);
        });

        assertTrue(exception.getMessage().contains("Question not found"));
        verify(questionRepository, times(1)).findById(1);
        verify(examRepository, times(0)).save(any(Exam.class));
    }

    @Test
    void testUpdateExistingExam() throws Exception {
        Exam exam = createMockExam(1);
        exam.setId(1);
        exam.setTitle("New Title");
        Question question = exam.getQuestions().get(0);

        Exam existingExam = new Exam();
        existingExam.setId(1);
        existingExam.setTitle("Old Title");

        when(questionRepository.findById(1)).thenReturn(Optional.of(question));
        when(examRepository.findById(1)).thenReturn(Optional.of(existingExam));
        when(examRepository.save(any(Exam.class))).thenReturn(existingExam);

        Exam updatedExam = examService.create(exam);

        assertNotNull(updatedExam);
        assertEquals("New Title", updatedExam.getTitle());
        verify(questionRepository, times(1)).findById(1);
        verify(examRepository, times(1)).findById(1);
        verify(examRepository, times(1)).save(existingExam);
    }

    @Test
    void testDeleteExamSuccess() {
        Integer examId = 1;
        Exam exam = new Exam();
        exam.setId(examId);

        when(examRepository.findById(examId)).thenReturn(Optional.of(exam));

        examService.delete(examId);

        verify(examRepository, times(1)).findById(examId);
        verify(examRepository, times(1)).delete(exam);
    }

    @Test
    void testDeleteExamNotFound() {
        Integer examId = 1;

        when(examRepository.findById(examId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            examService.delete(examId);
        });

        assertTrue(exception.getMessage().contains("Exam not found"));
        verify(examRepository, times(1)).findById(examId);
        verify(examRepository, times(0)).delete(any(Exam.class));
    }

    @Test
    void testDeleteExamThrowsException() {
        Integer examId = 1;
        Exam exam = new Exam();
        exam.setId(examId);

        when(examRepository.findById(examId)).thenReturn(Optional.of(exam));
        doThrow(new RuntimeException("Database error")).when(examRepository).delete(exam);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            examService.delete(examId);
        });

        assertTrue(exception.getMessage().contains("Error when deleting exam"));
        verify(examRepository, times(1)).findById(examId);
        verify(examRepository, times(1)).delete(exam);
    }

    @Test
    void testFindAllExams() {
        List<Exam> exams = Collections.singletonList(new Exam());
        when(examRepository.findAll()).thenReturn(exams);

        List<Exam> result = examService.findAll();

        assertEquals(exams, result);
    }

    @Test
    void testFindExamById() {
        Exam exam = new Exam();
        when(examRepository.findById(anyInt())).thenReturn(Optional.of(exam));

        Exam result = examService.findById(1);

        assertEquals(exam, result);
    }

    @Test
    void testFindExamById_NotFound() {
        when(examRepository.findById(anyInt())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            examService.findById(1);
        });

        assertEquals("ExamServiceImpl - findById: Exam not found", exception.getMessage());
        verify(examRepository, times(1)).findById(anyInt());
    }

    @Test
    void testStartExam_UserNotFound() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            examService.startExam(1, 1);
        });

        assertEquals("User not found with ID: 1", exception.getMessage());
        verify(userRepository, times(1)).findById(anyInt());
    }

    @Test
    void testStartExam_ExamNotFound() {
        User user = createMockUser();
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
        when(examRepository.findById(anyInt())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            examService.startExam(1, 1);
        });

        assertEquals("Exam not found with ID: 1", exception.getMessage());
        verify(userRepository, times(1)).findById(anyInt());
        verify(examRepository, times(1)).findById(anyInt());
    }

    @Test
    void testAnswerExam() {
        Question question = new Question();
        StartedExam startedExam = new StartedExam();
        ExamAnswer examAnswer = new ExamAnswer();

        when(questionRepository.findById(anyInt())).thenReturn(Optional.of(question));
        when(startedExamRepository.findById(anyLong())).thenReturn(Optional.of(startedExam));
        when(examAnswerRepository.findByStartedExamAndQuestion(any(StartedExam.class),
                any(Question.class))).thenReturn(Optional.empty());
        when(examAnswerRepository.save(any(ExamAnswer.class))).thenReturn(examAnswer);

        ExamAnswer result = examService.answerExam(1L, 1, Collections.singletonList("answer"));

        assertEquals(examAnswer, result);
        verify(examAnswerRepository, times(1)).save(any(ExamAnswer.class));
    }

    @Test
    void testResultExamStarted() {
        // Mock data
        User user = new User();
        Exam exam = new Exam();
        StartedExam startedExam = new StartedExam();
        Question question = new Question();
        question.setAnswer(List.of("answer"));
        exam.setQuestions(List.of(question));
        ExamAnswer examAnswer = new ExamAnswer();
        examAnswer.setAnswer(List.of("answer"));

        when(startedExamRepository.findByExamAndUser(exam, user))
                .thenReturn(Optional.of(startedExam));
        when(examAnswerRepository.findByStartedExamAndQuestion(startedExam, question))
                .thenReturn(Optional.of(examAnswer));

        // Call the method
        int result = examService.result(user, exam);

        // Verify the result
        Assertions.assertEquals(1, result);
    }

    @Test
    void testResultExamNotStarted() {
        // Mock data
        User user = new User();
        Exam exam = new Exam();

        when(startedExamRepository.findByExamAndUser(exam, user)).thenReturn(Optional.empty());

        // Call the method
        int result = examService.result(user, exam);

        // Verify the result
        Assertions.assertEquals(0, result);
    }

    @Test
    void testResultExamAnswerNotMatching() {
        // Mock data
        User user = new User();
        Exam exam = new Exam();
        StartedExam startedExam = new StartedExam();
        Question question = new Question();
        question.setAnswer(List.of("answer"));
        exam.setQuestions(List.of(question));
        ExamAnswer examAnswer = new ExamAnswer();
        examAnswer.setAnswer(List.of("wrong_answer"));

        when(startedExamRepository.findByExamAndUser(exam, user))
                .thenReturn(Optional.of(startedExam));
        when(examAnswerRepository.findByStartedExamAndQuestion(startedExam, question))
                .thenReturn(Optional.of(examAnswer));

        // Call the method
        int result = examService.result(user, exam);

        // Verify the result
        Assertions.assertEquals(0, result);
    }

    @Test
    void testResultExamNoAnswer() {
        // Mock data
        User user = new User();
        Exam exam = new Exam();
        StartedExam startedExam = new StartedExam();
        Question question = new Question();
        question.setAnswer(List.of("answer"));
        exam.setQuestions(List.of(question));

        when(startedExamRepository.findByExamAndUser(exam, user))
                .thenReturn(Optional.of(startedExam));
        when(examAnswerRepository.findByStartedExamAndQuestion(startedExam, question))
                .thenReturn(Optional.empty());

        // Call the method
        int result = examService.result(user, exam);

        // Verify the result
        Assertions.assertEquals(0, result);
    }

}
