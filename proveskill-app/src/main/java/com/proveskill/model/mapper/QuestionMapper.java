package com.proveskill.model.mapper;

import com.proveskill.model.dto.QuestionDto;
import com.proveskill.model.entity.QuestionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface QuestionMapper {

    public static final QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    public abstract QuestionEntity dtoToEntity(QuestionDto questionDto);

    public abstract QuestionDto entityToDto(QuestionEntity questionEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    public abstract QuestionEntity updateEntity(QuestionEntity questionEntityOrig, @MappingTarget QuestionEntity questionEntityDest);
}
