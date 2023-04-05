package com.proveskill.model.mapper;

import com.proveskill.model.dto.UserDto;
import com.proveskill.model.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public abstract UserEntity dtoToEntity(UserDto userDto);

    public abstract UserDto entityToDto(UserEntity user);

    @Mapping(target = "id", ignore = true)
    public abstract UserEntity updateEntity(UserEntity userEntityOrig, @MappingTarget UserEntity userEntityDest);
}
