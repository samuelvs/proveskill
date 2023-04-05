package com.proveskill.service.question;

import com.proveskill.model.dto.QuestionDto;
import com.proveskill.model.dto.UserDto;
import com.proveskill.model.entity.QuestionEntity;
import com.proveskill.model.entity.UserEntity;
import com.proveskill.model.mapper.QuestionMapper;
import com.proveskill.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public QuestionDto create(QuestionDto obj) throws Exception {
        try {
            QuestionEntity questionEntity = QuestionMapper.INSTANCE.dtoToEntity(obj);

            if(questionEntity.getId() != null) {
                QuestionEntity userEntityFound = this.questionRepository.findById(questionEntity.getId()).orElseThrow(
                        () -> new Exception("QuestionServiceImpl - create/update: Question not found")
                );
                QuestionEntity questionEntityUpdated = QuestionMapper.INSTANCE.updateEntity(questionEntity, userEntityFound);
                questionEntityUpdated.setUpdatedAt(LocalDateTime.now());
                return QuestionMapper.INSTANCE.entityToDto(this.questionRepository.saveAndFlush(questionEntityUpdated));
            }

            questionEntity.setCreatedAt(LocalDateTime.now());
            questionEntity.setUpdatedAt(LocalDateTime.now());
            QuestionEntity questionSaved = this.questionRepository.save(questionEntity);
            return QuestionMapper.INSTANCE.entityToDto(questionSaved);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("QuestionServiceImpl - create: Error when create question: {}", e);
        }
    }

    @Override
    public void delete(long id) {
        try {
            Optional<QuestionEntity> questionEntity = this.questionRepository.findById(id);
            if(questionEntity.isPresent()) {
                this.questionRepository.delete(questionEntity.get());
            } else {
                throw new Exception("QuestionServiceImpl - delete: Question not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("QuestionServiceImpl - delete: Error when delete question: {}", e);
        }
    }

    @Override
    public List<QuestionDto> findAll() {
        List<QuestionEntity> questionEntities = this.questionRepository.findAll();
        return questionEntities.stream().map(this::formatQuestion).toList();
    }

    @Override
    public QuestionDto findById(long id) {
        return this.questionRepository.findById(id).map(this::formatQuestion).orElseThrow(
                () -> new RuntimeException("QuestionServiceImpl - findById: Question not found")
        );
    }

    private QuestionDto formatQuestion(QuestionEntity questionDto) {
        if (questionDto.getUser() == null) {
            questionDto.setUser(
                    UserEntity.builder()
                            .name("ADMIN")
                            .email("admin@proveskill.com")
                            .userType(3L)
                            .phone(123456789L)
                            .build()
            );
        }
        return QuestionMapper.INSTANCE.entityToDto(questionDto);
    }
}
