package com.proveskill.pwebproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.proveskill.pwebproject.model.Exam;
import com.proveskill.pwebproject.model.ExamAnswer;
import com.proveskill.pwebproject.model.Question;
import com.proveskill.pwebproject.model.StartedExam;
import com.proveskill.pwebproject.model.User;
import com.proveskill.pwebproject.repository.ExamAnswerRepository;
import com.proveskill.pwebproject.repository.ExamRepository;
import com.proveskill.pwebproject.repository.QuestionRepository;
import com.proveskill.pwebproject.repository.StartedExamRepository;
import com.proveskill.pwebproject.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final StartedExamRepository startedExamRepository;
    private final ExamAnswerRepository examAnswerRepository;

    @Autowired
    public ExamService(ExamRepository examRepository, QuestionRepository questionRepository,
            StartedExamRepository startedExamRepository, ExamAnswerRepository examAnswerRepository,
            UserRepository userRepository) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
        this.startedExamRepository = startedExamRepository;
        this.examAnswerRepository = examAnswerRepository;
        this.userRepository = userRepository;
    }

    public Exam create(Exam exam) throws Exception {
        try {
            List<Question> questionEntities = exam
                    .getQuestions().stream().map(
                            question -> this.questionRepository.findById(question.getId())
                                    .orElseThrow(() -> new RuntimeException(
                                            "ExamServiceImpl - create: Question not found")))
                    .toList();
            exam.setQuestions(questionEntities);

            if (exam.getId() != null) {
                Exam examFound = this.examRepository.findById(exam.getId()).orElseThrow(
                        () -> new Exception("ExamServiceImpl - create/update: Exam not found"));

                examFound.setTitle(exam.getTitle());
                examFound.setDuration(exam.getDuration());
                examFound.setStartDateTime(exam.getStartDateTime());
                examFound.setEndDateTime(exam.getEndDateTime());

                return this.examRepository.save(examFound);
            }

            return this.examRepository.save(exam);
        } catch (Exception e) {
            throw new RuntimeException("ExamServiceImpl - create: Error when create exam: {}", e);
        }
    }

    public void delete(Integer id) {
        try {
            Optional<Exam> examEntity = this.examRepository.findById(id);
            if (examEntity.isPresent()) {
                this.examRepository.delete(examEntity.get());
            } else {
                throw new Exception("ExamServiceImpl - delete: Exam not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("ExamServiceImpl - delete: Error when delete exam: {}", e);
        }
    }

    public List<Exam> findAll() {
        List<Exam> examEntities = this.examRepository.findAll();
        return examEntities;
    }

    public Exam findById(Integer id) {
        return this.examRepository.findById(id).orElseThrow(
                () -> new RuntimeException("ExamServiceImpl - findById: Exam not found"));
    }

    public StartedExam startExam(Integer examId, Integer userId) {
        Optional<User> userOpt = this.userRepository.findById(userId);
        Optional<Exam> examOpt = this.examRepository.findById(examId);

        if (userOpt.isEmpty()) {
            throw new EntityNotFoundException("User not found with ID: " + userId);
        }
        if (examOpt.isEmpty()) {
            throw new EntityNotFoundException("Exam not found with ID: " + examId);
        }

        User user = userOpt.get();
        Exam exam = examOpt.get();

        Optional<StartedExam> startedExamEntityFound =
                this.startedExamRepository.findByExamAndUser(exam, user);
        if (startedExamEntityFound.isEmpty()) {
            StartedExam startedExamEntity = StartedExam.builder().exam(exam).user(user)
                    .startedAt(LocalDateTime.now()).build();

            this.startedExamRepository.save(startedExamEntity);
            startedExamEntityFound = this.startedExamRepository.findByExamAndUser(exam, user);
        }

        StartedExam startedExam = startedExamEntityFound.get();
        startedExam.setExam(this.fillQuestionsToStudentExam(startedExam, startedExam.getExam()));
        return startedExam;
    }

    public List<StartedExam> findAllStartedExams() {
        List<StartedExam> startedExams = this.startedExamRepository.findAll();
        for (StartedExam started : startedExams) {
            started.setExam(this.fillQuestionsToExaList(started, started.getExam()));
        }

        return startedExams;
    }

    public ExamAnswer answerExam(Long startExamId, Integer questionId, List<String> answer) {
        Optional<Question> questionOpt = this.questionRepository.findById(questionId);
        Optional<StartedExam> startedExamOpt = this.startedExamRepository.findById(startExamId);

        if (questionOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Question not found with ID: " + questionId);
        }
        if (startedExamOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Started exam not found with ID: " + startExamId);
        }

        Question question = questionOpt.get();
        StartedExam startedExam = startedExamOpt.get();

        Optional<ExamAnswer> examAnswerFound =
                this.examAnswerRepository.findByStartedExamAndQuestion(startedExam, question);
        if (examAnswerFound.isEmpty()) {
            ExamAnswer examAnswer = ExamAnswer.builder().startedExam(startedExam).question(question)
                    .answer(answer).build();

            return this.examAnswerRepository.save(examAnswer);
        }

        ExamAnswer examAnswer = examAnswerFound.get();
        examAnswer.setAnswer(answer);
        return this.examAnswerRepository.save(examAnswer);
    }

    public Exam fillQuestionsToStudentExam(StartedExam startedExam, Exam exam) {
        List<Question> questions = new ArrayList<>();
        for (Question question : exam.getQuestions()) {
            Optional<ExamAnswer> examAnswer =
                    this.examAnswerRepository.findByStartedExamAndQuestion(startedExam, question);
            if (examAnswer.isEmpty()) {
                question.setAnswer(null);
            } else {
                question.setAnswer(examAnswer.get().getAnswer());
            }
            question.setUser(null);
            question.setCreatedAt(null);
            question.setUpdatedAt(null);
            question.setTags(null);
            question.setTags(null);
            questions.add(question);
        }
        exam.setQuestions(questions);
        return exam;
    }

    public Exam fillQuestionsToExaList(StartedExam startedExam, Exam exam) {
        List<Question> questions = new ArrayList<>();
        for (Question question : exam.getQuestions()) {
            Optional<ExamAnswer> examAnswer =
                    this.examAnswerRepository.findByStartedExamAndQuestion(startedExam, question);
            question.setUserAnswer(examAnswer.get().getAnswer());
            questions.add(question);
        }
        exam.setQuestions(questions);
        return exam;
    }

    public Integer result(User user, Exam exam) {
        Optional<StartedExam> startedExamFounded =
                this.startedExamRepository.findByExamAndUser(exam, user);
        if (!startedExamFounded.isPresent()) {
            return 0;
        }
        StartedExam startedExam = startedExamFounded.get();
        Integer result = 0;

        for (Question question : exam.getQuestions()) {
            Optional<ExamAnswer> examAnswerOpt =
                    this.examAnswerRepository.findByStartedExamAndQuestion(startedExam, question);

            if (examAnswerOpt.isPresent()) {
                ExamAnswer examAnswer = examAnswerOpt.get();

                if (compareList(question.getAnswer(), examAnswer.getAnswer())) {
                    result++;
                }
            }
        }

        return result;
    }

    public static boolean compareList(List ls1, List ls2) {
        return ls1.toString().contentEquals(ls2.toString()) ? true : false;
    }
}
